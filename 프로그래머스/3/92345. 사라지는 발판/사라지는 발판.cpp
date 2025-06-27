#include <string>
#include <vector>
#include <iostream>

using namespace std;

int dr[] = {-1, 1, 0, 0};
int dc[] = {0, 0, -1, 1};

int R, C;
vector<vector<int>> board;

int dfs(bool is_a_turn, pair<int, int> pa, pair<int, int> pb) {
    
    // 승부가 난 경우 더 움직일 수 없으므로 0 리턴
    if (board[pa.first][pa.second] == 0 || board[pb.first][pb.second] == 0) {
        return 0;
    }
    
    // 이후 턴에서 최선의 수를 저장하기위한 변수
    // 이후 재귀에서 진다는 결과가 나오면 최대 이동 횟수를, 이긴다는 결과가 나오면 최소 이동 횟수를 저장
    int best_steps = 0;
    
    int r = (is_a_turn) ? pa.first : pb.first;
    int c = (is_a_turn) ? pa.second : pb.second;
    for(int i=0; i<4; i++) {
        int nr = r + dr[i];
        int nc = c + dc[i];
        int steps; // 이후 게임을 진행할 때의 이동 횟수
        
        // 이동 가능할 경우 이동
        if (nr >=0 && nc >=0 && nr < R && nc < C && board[nr][nc]) {
            board[r][c] = 0;
            if (is_a_turn)
                steps = dfs(!is_a_turn, make_pair(nr, nc), pb) + 1;
            else
                steps = dfs(!is_a_turn, pa, make_pair(nr, nc)) + 1;
            board[r][c] = 1;
            
            // 지는 경우에 대한 최선의 수를 저장하다가, 이기는 경우를 발견한 경우 -> 이기는게 최선이므로 업데이트
            if (best_steps%2 == 0 && steps%2 == 1) {
                best_steps = steps;
            }
            // 지는 경우에 대한 최선의 수를 저장하다가, 지는 경우를 발견한 경우 -> 더 많이 움직인 쪽으로 업데이트
            else if (best_steps%2 == 0 && steps%2 == 0) {
                best_steps = max(best_steps, steps);
            }
            // 이기는 경우에 대한 최선의 수를 저장하다가, 이기는 경우를 발견한 경우 -> 더 적게 움직인 쪽으로 업데이트
            else if (best_steps%2 == 1 && steps%2 == 1) {
                best_steps = min(best_steps, steps);
            }
            
        }
    }
    
    // 위 for문에 걸리지 않았다면 사방으로 못 움직이는 것. best_steps가 0인 채로 리턴
    return best_steps;
}

int solution(vector<vector<int>> _board, vector<int> aloc, vector<int> bloc) {
    board = _board;
    R = board.size();
    C = board[0].size();
    // 항상 A가 먼저 시작
    // A와 B가 그냥 번갈아 자유롭게 움직이면서, 승부가 났을 때 최선으로 움직인 경우를 카운트
    // 이겼을 때는 가장 적게 움직이고, 졌을 때는 가장 많이 움직여야 하므로 이를 처리할 조건이 필요
    
    pair<int, int> pa = make_pair(aloc[0], aloc[1]);
    pair<int, int> pb = make_pair(bloc[0], bloc[1]);
    int result = dfs(true, pa, pb);
    
    return result;
}