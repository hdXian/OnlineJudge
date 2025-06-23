import java.util.*;

class Solution {
    
    static int[][] graph;
    static int[] distances;
    static final int INF = 490001;
    
    static class Node implements Comparable<Node> {
        int num, cost;
        public Node(int num, int cost) {
            this.num = num;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Node n) {
            return this.cost - n.cost;
        }
    }
    
    public int solution(int N, int[][] road, int K) {
        // 다익스트라를 돌려 최단 거리가 K 이하인 노드들의 개수 세기
        // N: 1 ~ 50
        // road 길이: 1 ~ 2천
        
        // 1. 인접 행렬을 초기화한다.
        graph = new int[N+1][N+1];
        for(int i=0; i<N+1; i++) {
            Arrays.fill(graph[i], INF);
            graph[i][i] = 0;
        }
        
        int n1, n2, cost;
        for(int[] edge: road) {
            n1 = edge[0];
            n2 = edge[1];
            cost = edge[2];
            graph[n1][n2] = graph[n2][n1] = Math.min(graph[n1][n2], cost); // 더 작은 값으로만 저장
        }
        
        // 2. 최단거리 배열을 초기화한다.
        distances = new int[N+1];
        Arrays.fill(distances, INF);
        distances[1] = 0;
        
        // 3. 다익스트라를 돌린다.
        // PriorityQueue<Node> pq = new PriorityQueue<>();
        Queue<Node> pq = new LinkedList<>();
        pq.add(new Node(1, 0));
        
        while(!pq.isEmpty()) {
            Node cur = pq.poll();
            
            int n = cur.num;
            int c = cur.cost;
            for(int i=1; i<=N; i++) {
                
                if (graph[n][i] != INF) {
                    int new_cost = c + graph[n][i];
                    if (new_cost < distances[i]) {
                        distances[i] = new_cost;
                        pq.add(new Node(i, new_cost));
                    }
                }
                
            }
        }
        
        int count = 0;
        for(int i=1; i<=N; i++) if (distances[i] <= K) count++;
        
        return count;
    }
}