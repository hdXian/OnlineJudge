import java.util.*;
import java.io.*;

class Solution {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int result;
    static int N; // 보드 크기. 1 ~ 100
    static int[][] board;
    static final int INF = 100000; // 거리 최댓값은 9 * 100 * 100 = 9만

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static class Node implements Comparable<Node> {
        int row;
        int col;
        int cost;
        public Node(int row, int col, int cost) {
            this.row = row;
            this.col = col;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node n) {
            return Integer.compare(this.cost, n.cost); // 비용 순으로 오름차순
        }

    }

    static int dijkstra() {
        // 1. 출발 노드의 거리를 업데이트하고 우선순위 큐에 넣는다.
        // 2. 큐에 들어간 출발 노드를 poll하고, poll한 노드의 인접 노드들을 큐에 집어넣는다.
        // 2-1. 단, 최단 거리가 업데이트 된 노드만 집어넣는다.
        // 3. 큐가 빌 때까지 과정을 반복한다.
        int[][] distance = new int[N][N];
        for(int i=0; i<N; i++) Arrays.fill(distance[i], INF);

        distance[0][0] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0, 0, 0));

        // 큐가 빌 때까지 반복
        // 큐가 비었다는 것은 더 이상 업데이트할 노드 거리 정보가 없는 뜻으로, 모든 노드에 대한 최단거리 계산이 완료되었다는 것을 의미함.
        while(!pq.isEmpty()) {
            Node cur = pq.poll();

            // 상하좌우가 모두 인접노드
            int nr, nc;
            for (int i=0; i<4; i++) {
                nr = cur.row + dr[i];
                nc = cur.col + dc[i];
                if (nr >= 0 && nc >= 0 && nr < N && nc < N) {
                    // 나를 거쳐서 인접 노드에 가는 거리와 현재까지 저장된 인접 노드로 가는 최단거리를 비교
                    // 나를 거쳐가는게 현재까지 인접노드로 가는 최단거리보다 더 짧으면 업데이트하고 pq에 추가
                    if (cur.cost + board[nr][nc] < distance[nr][nc]) {
                        distance[nr][nc] = cur.cost + board[nr][nc];
                        pq.add(new Node(nr, nc, distance[nr][nc]));
                    }
                }

            }

        }

        return distance[N-1][N-1];
    }

    public static void main(String[] args) throws Exception {
		int T = Integer.parseInt(reader.readLine());

        StringBuilder sb = new StringBuilder();

        for (int test_case = 1; test_case <= T; test_case++) {

            N = Integer.parseInt(reader.readLine());
            board = new int[N][N];

            for(int i=0; i<N; i++) {
                char[] arr = reader.readLine().toCharArray();
                for(int j=0; j<N; j++) {
                    board[i][j] = Character.getNumericValue(arr[j]);
                }
            }

            int result = dijkstra();
            sb.append(String.format("#%d %d\n", test_case, result));
        }

        System.out.println(sb);

    }
}
