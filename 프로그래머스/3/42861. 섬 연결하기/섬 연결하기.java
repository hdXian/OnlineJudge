import java.io.*;
import java.util.*;
import java.math.*;

class Solution {

    static int[] parents;

    static class Edge implements Comparable<Edge> {
        public int p1;
        public int p2;
        public int cost;

        public Edge(int p1, int p2, int cost) {
            this.p1 = p1;
            this.p2 = p2;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge e) {
            return Integer.compare(this.cost, e.cost);
        }

    }

    static int findParent(int n) {
        if (parents[n] != n) parents[n] = findParent(parents[n]);
        return parents[n];
    }

    static boolean union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);

        if (p1 == p2) return false;
        
        if (p1 < p2) parents[p2] = p1;
        else parents[p1] = p2;

        return true;
    }

    public int solution(int n, int[][] costs) {
        // 섬 개수 n: 1 ~ 100
        // costs[i]: 번호1, 번호2, 비용]
        // uinon-find 기반 MST.

        parents = new int[n];
        for(int i=0; i<n; i++) {
            parents[i] = i;
        }

        // 1. 도로 정보를 받아 Edge 클래스로 저장한다.
        // 2. Edge들을 비용이 적은 순으로 정렬하고, union-find를 이용하여 모든 섬이 포함될 때까지 Edge를 선택한다.
        // 3. 모든 Edge가 선택될 때까지 합한 간선 비용을 리턴한다.
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        int p1, p2, cost;
        for(int i=0; i<costs.length; i++) {
            p1 = costs[i][0];
            p2 = costs[i][1];
            cost = costs[i][2];
            pq.add(new Edge(p1, p2, cost));
        }

        int edge_count = 0;
        int total_cost = 0;
        while(!pq.isEmpty() && edge_count < (n-1)) {
            Edge e = pq.poll();
            if (union(e.p1, e.p2)) {
                // System.out.printf("%d과 %d 사이의 간선이 추가됨\n", e.p1, e.p2);
                edge_count++;
                total_cost += e.cost;
            }
        }

        return total_cost;


    }

}