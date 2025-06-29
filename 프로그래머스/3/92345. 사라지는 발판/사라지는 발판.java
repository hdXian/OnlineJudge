import java.util.*;

class Solution {
    
    static int R, C;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    
    static int[][] board;
    
    // 지금까지 양쪽이 움직인 횟수를 리턴
    static int dfs(boolean is_a_turn, int ar, int ac, int br, int bc) {
        
        // 승부가 정해지면 더 이상 움직이지 못하고 0 리턴
        if (board[ar][ac] == 0 || board[br][bc] == 0) {
            return 0;
        }
        
        // 최선의 수까지 진행된 차례 수를 저장
        int best = 0;
        
        int r = (is_a_turn) ? ar : br;
        int c = (is_a_turn) ? ac : bc;
        
        for(int i=0; i<4; i++) {
            int turns = 0;
            
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (nr >=0 && nc >=0 && nr < R && nc < C && board[nr][nc] == 1) {
                board[r][c] = 0;
                if (is_a_turn)
                    turns = dfs(!is_a_turn, nr, nc, br, bc) + 1; // 이동하고 턴을 넘긴다. 이동횟수가 1 추가된다.
                else
                    turns = dfs(!is_a_turn, ar, ac, nr, nc) + 1; // 이동하고 턴을 넘긴다. 이동횟수가 1 추가된다.
                
                board[r][c] = 1; // 백트래킹
            }
            
            // best가 짝수로 저장돼있다 -> 지금껏 찾은 최선의 수가 자신의 패배를 기준으로 저장돼있다
            if (best%2 == 0) {
                // turns의 홀짝 여부에 따라 현재 플레이어(is_a_turn 기준 A, B)가 마지막까지 가서 이겼는지, 졌는지 알 수 있다
                if (turns%2 == 1) best = turns; // 이긴 경우를 찾았다 -> 바로 업데이트
                else best = Math.max(best, turns); // 진 또 다른 경우를 찾았다 -> 더 많이 움직인 경우로 업데이트
            }
            // best가 홀수로 저장돼있다 -> 지금껏 찾은 최선의 수가 승리를 기준으로 저장돼있다.
            else {
                // 패배한 경우를 찾았다 -> 무시한다. 이긴 경우가 있는데 지는 경우로 업데이트 할 이유가 없다.
                // 이긴 또 다른 경우를 찾았다 -> 더 적게 움직인 경우로 업데이트
                if (turns%2 == 1) best = Math.min(best, turns);
            }
            
        }
        
        // 현재 위치에서 사방으로 움직지 못한 경우 best는 0에서 업데이트되지 않는다. 패배와 같이 0이 리턴된다.
        return best;
    }
    
    public int solution(int[][] _board, int[] aloc, int[] bloc) {
        R = _board.length;
        C = _board[0].length;
        board = _board;
        
        // 1. 게임이 끝날 때까지 번갈아가며 수를 둔다. 이 떄 각자 사방으로 이동해보며 dfs한다.
        // 2. 게임이 끝난 경우, 몇 번 차례가 지나고 끝났는지를 통해 현재 플레이어의 승패 여부를 판단할 수 있다.
        // 3. 이긴 경우 최대한 적게 움직인 수를, 진 경우 최대한 많이 움직인 수를 재귀적으로 리턴해서 저장한다.
        int result = dfs(true, aloc[0], aloc[1], bloc[0], bloc[1]);
        return result;
    }
}