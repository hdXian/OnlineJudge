import java.util.*;

class Solution {
    static int N;
    static int result = 0;
    static int dr[] = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int dc[] = {0, 1, 1, 1, 0, -1, -1, -1};
    
    static boolean check(List<Integer> cur) {
        boolean[][] board = new boolean[N][N];
        
        for(int c=0; c<cur.size(); c++) {
            int row = c;
            int col = cur.get(c);
            
            if (board[row][col]) return false; // 퀸을 놓지 못하면 false 리턴
            board[row][col] = true;
            
            // 배치한 퀸 위치를 기준으로 놓지 못하는 위치 업데이트
            for(int i=0; i<8; i++) {
                int nr = row + dr[i];
                int nc = col + dc[i];
                while(nr >=0 && nc >=0 && nr < N && nc < N) {
                    board[nr][nc] = true;
                    nr += dr[i];
                    nc += dc[i];
                }
            }
        }
        
        // cur의 모든 위치에 퀸 배치를 성공했다면 true
        return true;
    }
    
    void dfs(List<Integer> cur, boolean[] visited) {
        if (!check(cur)) return;
        
        if (cur.size() == N) {
            result++;
            return;
        }
        
        for(int i=0; i<N; i++) {
            if(visited[i]) continue; // 같은 열에 배치는 기본적으로 불가하므로 건너뜀
            
            cur.add(i);
            visited[i] = true;
            
            dfs(cur, visited);
            
            cur.remove(cur.size()-1);
            visited[i] = false;
        }
        
        
    }
    
    public int solution(int n) {
        // n*n 체스판에 퀸 n개 배치
        N = n;
        // 퀸의 배치를 {x, y, z, w} 형태로 표현
        // 1행 x열, 2행 y열, 3행 z열, ...
        
        List<Integer> cur = new ArrayList<>();
        boolean[] visited = new boolean[N];
        Arrays.fill(visited, false);
        
        dfs(cur, visited);
        
        return result;
    }
}