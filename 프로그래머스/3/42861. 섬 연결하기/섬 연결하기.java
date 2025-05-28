import java.util.*;

class Solution {
    
    static class Edge implements Comparable<Edge> {
        int p1, p2;
        int cost;
        
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
    
    static int[] parents;
    static int[] ranks;
    
    static int findParent(int n) {
        if (parents[n] != n) parents[n] = findParent(parents[n]);
        return parents[n];
    }
    
    static boolean union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);
        
        if (p1 == p2) return false;
        
        int r1 = ranks[p1];
        int r2 = ranks[p2];
        if (r1 == r2) {
            parents[p2] = p1;
            ranks[p1]++;
        }
        else if (r1 > r2) {
            parents[p2] = p1;
        }
        else parents[p1] = p2;
        
        return true;
    }
    
    public int solution(int n, int[][] costs) {
        // n: 섬 개수. 1 ~ 100
        // costs: 다리들
        // costs[i][0]: 번호1, costs[i][1]: 번호2, costs[i][2]: 비용
        // 양방향 간선. 같은 간선 주어지지 않음.
        
        // MST 만들기
        // 1. 부모 배열과 랭크 배열을 초기화한다.
        parents = new int[n];
        ranks = new int[n];
        for(int i=0; i<n; i++) {
            parents[i] = i;
            ranks[i] = 0;
        }
        
        // 2. 간선 정보를 받아 비용 순으로 오름차순으로 정렬한다.
        int siz = costs.length;
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for(int i=0; i<siz; i++) {
            // 점1, 점2, 비용
            pq.add(new Edge(costs[i][0], costs[i][1], costs[i][2]));
        }
        
        // 3. 큐가 비거나 n-1개의 간선이 선택될 때까지 서로 다른 집합에 속한 간선을 선택한다.
        int edge_count = 0;
        int total_cost = 0;
        while(!pq.isEmpty() && edge_count < (n-1)) {
            Edge cur = pq.poll();
            if (union(cur.p1, cur.p2)) {
                total_cost += cur.cost;
                edge_count++;
            }
        }
        
        return total_cost;
    }

}