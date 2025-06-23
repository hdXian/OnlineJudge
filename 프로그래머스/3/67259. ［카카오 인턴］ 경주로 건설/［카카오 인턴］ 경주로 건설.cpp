#include <string>
#include <vector>
#include <iostream>

using namespace std;

#define INF 999999

int dr[] = {-1, 1, 0, 0};
int dc[] = {0, 0, -1, 1};

int N;
int table[25][25][4];
bool visited[25][25] = {false, };
vector<vector<int>> board_comp;

// pre_dir: 해당 좌표로 오기 직전의 진행방향. 상하좌우 0123
void dfs(int row, int col, int cost, int pre_dir) {
    // cost: 현재 위치까지 오는데 든 비용
    if (cost > table[row][col][pre_dir]) return;
    
    table[row][col][pre_dir] = cost;
    
    for(int i=0; i<4; i++) {
        
        int nr = row + dr[i];
        int nc = col + dc[i];
        
        if (nr >=0 && nc >=0 && nc < N && nr < N && board_comp[nr][nc] == 0) {
            if (!visited[nr][nc]) {
                visited[nr][nc] = true;
                
                // 시작점인 경우
                if (row == 0 && col == 0) {
                    dfs(nr, nc, cost+100, i);
                }
                // 직전 방향과 같은 방향으로 진행할 경우
                else if (i == pre_dir) {
                    dfs(nr, nc, cost+100, i);
                }
                // 직전 방향과 다른 방향으로 꺾을 경우
                // 방향이 좌 -> 우, 상 -> 하 이런 식으로 바뀌진 않음. visited에서 걸러짐.
                else {
                    dfs(nr, nc, cost+600, i);
                }
                
                visited[nr][nc] = false;
            }
        }
    }
    
}

int solution(vector<vector<int>> board) {
    // 전역변수로 올리기
    N = board.size();
    board_comp = board;
    
    // 1. dp 테이블을 초기화한다.
    for(int i=0; i<25; i++) {
        for(int j=0; j<25; j++) {
            for(int k=0; k<4; k++) {
                table[i][j][k] = INF;
            }
        }
    }
    
    // 2. 시작 지점의 비용을 0으로 초기화한다.
    for(int i=0; i<4; i++) {
        table[0][0][i] = 0;
    }
    visited[0][0] = true;
    
    // 3. dfs로 완탐을 돌면서 최소 비용을 저장한다.
    dfs(0, 0, 0, 0);
    
    int result = table[N-1][N-1][0];
    for(int i=0; i<4; i++) result = min(result, table[N-1][N-1][i]);
    return result;
}

