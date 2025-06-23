import java.util.*;

class Solution {
    
    static int[][] boardSt;
    static int N;
    static int[][][] table; // 각 진입 방향별로 최단 거리를 저장한다.
    static final int INF = 999999;
    
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    
    static class Node {
        int row, col;
        int dir; // 현재 좌표에 도달하기 직전의 진입 방향
        int cost;
        public Node(int row, int col, int dir, int cost) {
            this.row = row;
            this.col = col;
            this.dir = dir;
            this.cost = cost;
        }
    }
    
    static void bfs(int row, int col, int direction) {
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(row, col, direction, 0));
        
        while(!q.isEmpty()) {
            Node cur = q.poll();
            
            int r = cur.row;
            int c = cur.col;
            int dir = cur.dir;
            int cost = cur.cost;
            
            for(int i=0; i<4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                if (nr >=0 && nc >=0 && nr < N && nc < N && boardSt[nr][nc] == 0) {
                    // dir와 i가 같으면 진행 방향이 같다는 의미
                    // 직전 경로와 같으면 +100, 다르면 +600
                    // 상-하, 좌-우 변경은 되돌아가는 움직임인데, 거리 +600으로 처리되므로 사실상 업데이트 안 됨
                    int new_cost = (dir == i) ? cost+100 : cost+600;
                    if (new_cost < table[nr][nc][i]) {
                        table[nr][nc][i] = new_cost;
                        q.add(new Node(nr, nc, i, new_cost));
                    }
                }
            }
            
        }
        
    }
    
    public int solution(int[][] board) {
        N = board.length;
        boardSt = board;
        table = new int[N][N][4];
        
        // 1. dp 테이블을 초기화한다.
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                for(int k=0; k<4; k++)
                    table[i][j][k] = INF;
            }
        }
        
        for(int i=0; i<4; i++) table[0][0][i] = 0;
        
        // 2. bfs를 돌려 최단 거리를 찾는다.
        bfs(0, 0, 1);
        bfs(0, 0, 3);
        
        int result = table[N-1][N-1][0];
        for(int i=0; i<4; i++) result = Math.min(result, table[N-1][N-1][i]);
        
        return result;
    }
}