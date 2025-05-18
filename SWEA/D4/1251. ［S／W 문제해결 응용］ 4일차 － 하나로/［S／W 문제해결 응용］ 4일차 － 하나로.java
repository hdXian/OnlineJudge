import java.util.*;
import java.io.*;
import java.math.*;

class Solution {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N; // 섬 개수. 1 ~ 1천
    static double E;
    static int[] xs;
    static int[] ys;

    static int[] parents;
    static int edge_count;

    static class Edge implements Comparable<Edge> {
        public int p1;
        public int p2;
        public double cost;

        public Edge(int p1, int p2, double cost) {
            this.p1 = p1;
            this.p2 = p2;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge e) {
            return Double.compare(this.cost, e.cost); // 비용 순으로 오름차순
        }

    }

    static double calcCost(int p1, int p2) {
        double nx = Math.pow(Math.abs(xs[p2] - xs[p1]), 2);
        double ny = Math.pow(Math.abs(ys[p2] - ys[p1]), 2);

        return E * Math.pow(Math.sqrt(nx+ny), 2); // 세율 * 길이 제곱
    }

    static int findParent(int n) {
        if (parents[n] != n) parents[n] = findParent(parents[n]);
        return parents[n];
    }

    static boolean union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);

        if (p1==p2) return false;

        // 더 작은놈이 부모
        if (p1 < p2) parents[p2] = p1;
        else parents[p1] = p2;
        return true;
    }

    static double calc() {

        // 모든 점들 간의 간선을 생성한다.
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        for(int i=0; i<N; i++) {
            for(int j=i+1; j<N; j++) {
                double cost = calcCost(i, j);
                pq.add(new Edge(i, j, cost));
            }
        }

        parents = new int[N];
        for(int i=0; i<N; i++) parents[i] = i;

        double total_cost = 0;
        edge_count = 0;
        while (!pq.isEmpty() && edge_count < N-1) {
            Edge tmp = pq.poll();
            if (union(tmp.p1, tmp.p2)) {
                edge_count++;
                total_cost += tmp.cost;
            }
        }

        if ((total_cost * 10 % 10) >= 5) {
            return Math.ceil(total_cost);
        }
        else return Math.floor(total_cost);
    }

    public static void main(String[] args) throws Exception {
		int T = Integer.parseInt(reader.readLine());

        // 1. 모든 섬들에 대한 간선을 생성한다.
        // 2. 간선을 비용 순으로 정렬하고, MST를 돌린다.
        // 3. MST가 완성되었을때까지의 비용 합을 리턴한다.

        StringBuilder sb = new StringBuilder();

        StringTokenizer tkn;
        for(int test_case = 1; test_case <= T; test_case++) {
            N = Integer.parseInt(reader.readLine()); // 섬 개수. 1 ~ 1천
            xs = new int[N]; // x좌표들
            ys = new int[N]; // y좌표들

            tkn = new StringTokenizer(reader.readLine());
            for(int i=0; i<N; i++) xs[i] = Integer.parseInt(tkn.nextToken());
            tkn = new StringTokenizer(reader.readLine());
            for(int i=0; i<N; i++) ys[i] = Integer.parseInt(tkn.nextToken());

            E = Double.parseDouble(reader.readLine());

            long result = (long) calc();
            sb.append(String.format("#%d %d\n", test_case, result));

        }

        System.out.println(sb);

    }
}
