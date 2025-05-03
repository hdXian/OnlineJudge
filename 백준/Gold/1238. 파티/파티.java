import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N, M, X;

    static final int INF = 100001; // 길 비용의 최대는 100 * 1000

    static class Edge implements Comparable<Edge> {
        public int cost;
        public int dst;
        public Edge() {}
        public Edge(int cost, int dst) {
            this.cost = cost;
            this.dst = dst;
        }

        @Override
        public int compareTo(Edge e) {
            if (this.cost == e.cost) return Integer.compare(this.dst, e.dst); // cost가 같다면 도착지 인덱스가 작은 순
            else return Integer.compare(this.cost, e.cost); // cost 기준으로 오름차순
        }

        @Override
        public String toString() {
            return String.format("[cost=%d, dst=%d]", cost, dst);
        }
    }

    static List<List<Edge>> neighbors = new ArrayList<>();
    static int[][] tables; // 각 노드의 다익스트라용 최단 거리 테이블

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 마을 개수 (1 ~ 1천)
        M = Integer.parseInt(tkn.nextToken()); // 길 개수 (1 ~ 1만)
        X = Integer.parseInt(tkn.nextToken()); // 모일 곳 (1 ~ N)

        for(int i=0; i<N; i++)
            neighbors.add(new ArrayList<>());

        int s, r, t;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            s = Integer.parseInt(tkn.nextToken());
            r = Integer.parseInt(tkn.nextToken());
            t = Integer.parseInt(tkn.nextToken());
            neighbors.get(s-1).add(new Edge(t, r)); // 인접 리스트에 추가
        }

        tables = new int[N+1][N+1]; // 인덱스 1부터 쓰기
    }

    // 다익스트라
    static void dijkstra(int start) {
        // start번 노드에 대해 다익스트라 알고리즘을 돌린다.

        // 1. start번 노드에 대한 최단 거리 테이블 초기화
        int[] table = tables[start];
        Arrays.fill(table, INF);

        // 2. 노드를 저장할 우선순위 큐 초기화
        Queue<Edge> pq = new PriorityQueue<>();

        // 3. 시작 노드 초기화, 인접 노드 추가
        table[start] = 0;

        // 우선순위 큐에 시작 노드의 인접 노드들 추가
        for(Edge e: neighbors.get(start-1)) {
            table[e.dst] = e.cost;
            pq.add(e);
        }

        while(!pq.isEmpty()) {
            // pq에서 가장 먼저 나오는 노드가 현재 노드에서 가장 가까운 노드
            Edge cur = pq.poll();
            int idx = cur.dst;

            // 현재 노드의 인접 노드들에 대해 최단거리 테이블을 업데이트한다.
            for(Edge e: neighbors.get(idx-1)) {
                // (인접 노드의 현재 테이블 값), (현재 노드의 테이블 값 + 인접 노드까지의 비용) 중 더 비용이 적은 쪽으로 업데이트
                int next = e.dst;
                if (table[next] > table[idx] + e.cost) {
                    table[next] = table[idx] + e.cost;
                    pq.add(new Edge(table[next], next));
                }
            }

        }

    }

    static void calc() {
        int result = -1;
        for(int i=1; i<=N; i++)
            result = Math.max(result, tables[i][X] + tables[X][i]);

        System.out.println(result);
    }

    public static void main(String[] args) throws Exception {
        init();
        for(int i=1; i<=N; i++) dijkstra(i);
        calc();
    }

}
