import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static int N, M;
    static int[][] board;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static int total_lands;
    static List<Edge> edges = new ArrayList<>();

    static int[] parents;
    static int[] ranks;

    static class Edge implements Comparable<Edge> {
        int n1, n2;
        int cost;
        public Edge(int n1, int n2, int cost) {
            this.n1 = n1;
            this.n2 = n2;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge e) {
            return this.cost - e.cost;
        }
    }

    // 섬에 번호 붙이기 위한 bfs
    static void bfs(int row, int col, int number) {
        boolean[][] visited = new boolean[N][M];
        for(int i=0; i<N; i++) Arrays.fill(visited[i], false);

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{row, col});
        board[row][col] = number;
        visited[row][col] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i=0; i<4; i++) {
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];
                if (nr >=0 && nc >=0 && nr < N && nc < M && ! visited[nr][nc]) {
                    if (board[nr][nc] == -1) {
                        board[nr][nc] = number;
                        visited[nr][nc] = true;
                        q.add(new int[]{nr, nc});
                    }
                }
            }
        }

    }

    static void init() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken());
        M = Integer.parseInt(tkn.nextToken());

        board = new int[N][M];
        // 땅인 부분 -1로 저장하기
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<M; j++) {
                board[i][j] = (tkn.nextToken().equals("1") ? -1 : 0);
            }
        }

        // bsf로 섬에 번호 붙이기
        int seq = 1;
        total_lands = 0;
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if (board[i][j] == -1) {
                    bfs(i, j, seq);
                    seq++;
                }
            }
        }
        total_lands = seq-1; // 전체 섬 개수

        parents = new int[total_lands+1];
        ranks = new int[total_lands+1];
        for(int i=0; i<total_lands+1; i++) {
            parents[i] = i;
            ranks[i] = 1;
        }
    }

    static void makeEdge() {
        // 가로 방향으로 다리 찾기
        for(int row=0; row<N; row++) {
            // 해당 행에 존재하는 섬들을 추가
            Queue<Integer> lands = new LinkedList<>();
            for(int col=0; col<M; col++) {
                if (board[row][col] != 0) {
                    lands.add(board[row][col]);
                    while(col < M && board[row][col] != 0) col++;
                }
            }

            if (lands.size() < 2) continue; // 섬이 2개 미만이면 건너뜀
            int n1, n2, cost;
            int idx1 = 0;
            int idx2 = 0;
            n1 = lands.poll();
            while(!lands.isEmpty()) {
                while(idx1 < M && board[row][idx1] != n1) idx1++;
                while(idx1 < M && board[row][idx1] == n1) idx1++; // last idx of n1

                n2 = lands.poll();
                idx2 = idx1;
                while(idx2 < M && board[row][idx2] != n2) idx2++;

                cost = idx2 - idx1;
                if (cost >= 2) {
                    edges.add(new Edge(n1, n2, cost));
                }

                n1 = n2;
                idx1 = idx2;
            }

        }
        // 세로 방향으로 다리 찾기
        for(int col=0; col<M; col++) {
            // 해당 행에 존재하는 섬들을 추가
            Queue<Integer> lands = new LinkedList<>();
            for(int row=0; row<N; row++) {
                if (board[row][col] != 0) {
                    lands.add(board[row][col]);
                    while(row < N && board[row][col] != 0) row++;
                }
            }

            if (lands.size() < 2) continue; // 섬이 2개 미만이면 건너뜀
            int n1, n2, cost;
            int idx1 = 0;
            int idx2 = 0;
            n1 = lands.poll();
            while(!lands.isEmpty()) {
                while(idx1 < N && board[idx1][col] != n1) idx1++;
                while(idx1 < N && board[idx1][col] == n1) idx1++; // last idx of n1

                n2 = lands.poll();
                idx2 = idx1;
                while(idx2 < N && board[idx2][col] != n2) idx2++;

                cost = idx2 - idx1;
                if (cost >= 2) {
                    edges.add(new Edge(n1, n2, cost));
                }

                n1 = n2;
                idx1 = idx2;
            }

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

        int r1 = ranks[p1];
        int r2 = ranks[p2];
        if (r1 == r2) {
            parents[p2] = p1;
            ranks[p1]++;
        }
        else if (r1 > r2) parents[p2] = p1;
        else parents[p1] = p2;

        return true;
    }

    static int MST() {
        // 만들 수 있는 도로 개수가 N-1보다 적으면 -1 리턴
        if (edges.size() < total_lands-1) return -1;

        PriorityQueue<Edge> pq = new PriorityQueue<>(edges);

        int edge_count = 0;
        int total_cost = 0;
        while(!pq.isEmpty() && edge_count < total_lands-1) {
            Edge cur = pq.poll();
            if (union(cur.n1, cur.n2)) {
                edge_count++;
                total_cost += cur.cost;
            }
        }

        if (edge_count < total_lands-1) return -1;
        else return total_cost;
    }

    static int calc() {
        makeEdge();
        return MST();
    }

    public static void main(String[] args) throws Exception {
        // 1. 가로, 세로로 돌면서 건설 가능한 모든 다리를 저장한다.
        // 2. 노드-간선으로 표현하여 MST를 돌린다.
        // 2-1. MST가 만들어지면 간선 비용을 출력한다.
        // 2-2. MST가 만들어지지 않는 경우 -1을 출력한다.
        init();
        int result = calc();
        System.out.println(result);
    }

}
