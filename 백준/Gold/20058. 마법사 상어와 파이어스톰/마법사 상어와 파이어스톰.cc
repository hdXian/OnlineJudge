#include <iostream>
#include <cmath>
#include <vector>

using namespace std;

int N, Q;
vector<vector<int>> board;
vector<int> levels;

int dr[] = {-1, 1, 0, 0};
int dc[] = {0, 0, -1, 1};
vector<vector<bool>> visited;
vector<vector<bool>> is_melt;
int max_ice;
int total_ice;
int cur_ice;

void init() {
    cin >> N >> Q;
    N = (int)pow(2, N); // 줄 수. 1 ~ 6 -> (2 ~ 64)

    // new int[N][N]
    // 얼음판 입력받기
    board.resize(N);
    for(int i=0; i<N; i++) {
        board[i].resize(N);
        for(int j=0; j<N; j++) {
            cin >> board[i][j];
        }
    }
    
    // 시전 레벨 입력받기
    levels.resize(Q);
    int tmp;
    for(int i=0; i<Q; i++) {
        cin >> tmp; // 시전 레벨은 1 ~ N. -> (2 ~ 2^N)
        levels[i] = pow(2, tmp);
    }

    // dfs에 쓸 visited, is_melt 크기 초기화
    visited.resize(N);
    is_melt.resize(N);
    for(int i=0; i<N; i++) {
        visited[i].resize(N);
        is_melt[i].resize(N);
    }
}

void tornado(int sr, int sc, int siz) {
    int tmp_board[siz][siz];
    // 입력받기
    for(int r=0; r<siz; r++) {
        for(int c=0; c<siz; c++) {
            tmp_board[r][c] = board[sr+r][sc+c];
        }
    }

    // 1. 행, 열 뒤집기
    for(int r=0; r<siz; r++) {
        for(int c=r+1; c<siz; c++) {
            // r, c swap
            int tmp = tmp_board[r][c];
            tmp_board[r][c] = tmp_board[c][r];
            tmp_board[c][r] = tmp;
        }
    }

    // 2. 좌우 뒤집기
    for(int r=0; r<siz; r++) {
        for(int c=0; c<siz/2; c++) {
            int opp_col = siz-c-1;
            // swap
            int tmp = tmp_board[r][c];
            tmp_board[r][c] = tmp_board[r][opp_col];
            tmp_board[r][opp_col] = tmp;
        }
    }

    // 변경사항 다시 입력하기
    for(int r=0; r<siz; r++) {
        for(int c=0; c<siz; c++) {
            board[sr+r][sc+c] = tmp_board[r][c];
        }
    }
}

// dfs 돌려서 사방에 3군데 이상 얼음이 있는지 확인
void fire_ball(int row, int col) {
    visited[row][col] = true;
    int ice_count = 0;

    for(int i=0; i<4; i++) {
        int nr = row + dr[i];
        int nc = col + dc[i];
        if (nr >=0 && nc >=0 && nr < N && nc < N) {
            if (board[nr][nc] > 0) ice_count++;
            if (!visited[nr][nc]) fire_ball(nr, nc);
        }
    }

    // 주변을 둘러싼 얼음이 3개 미만인 경우 녹는 얼음으로 지정
    if (ice_count < 3) is_melt[row][col] = true;
}

void fire_storm(int level) {
    // 1. 토네이도로 얼음판 회전
    for(int row=0; row<N; row+=level) {
        for(int col=0; col<N; col+=level) {
            tornado(row, col, level);
        }
    }

    // 2. fireball로 조건에 따라 얼음 제거
    for(int i=0; i<N; i++) {
        fill(visited[i].begin(), visited[i].end(), false);
        fill(is_melt[i].begin(), is_melt[i].end(), false);
    }

    fire_ball(0, 0);

    for(int row=0; row<N; row++) {
        for(int col=0; col<N; col++) {
            if (is_melt[row][col]) board[row][col] = max(0, board[row][col]-1);
        }
    }
}

void find_ice_group(int row, int col) {
    visited[row][col] = true;

    if(board[row][col] == 0) return; // 얼음이 없으면 바로 리턴

    total_ice += board[row][col];
    cur_ice++;

    for(int i=0; i<4; i++) {
        int nr = row + dr[i];
        int nc = col + dc[i];
        if (nr >=0 && nc >=0 && nr < N && nc < N && !visited[nr][nc]) {
            find_ice_group(nr, nc);
        }
    }
}

string calc() {
    // 1. Q번 파이어스톰을 시전한다.
    for(int i=0; i<Q; i++) {
        fire_storm(levels[i]);
    }

    // 2. 남은 얼음들의 합을 구한다.
    // 3. 남아있는 얼음 중 가장 큰 덩어리의 크기를 구한다. (dfs)
    for(int i=0; i<N; i++)
        fill(visited[i].begin(), visited[i].end(), false);

    max_ice = 0;
    total_ice = 0;
    for(int i=0; i<N; i++) {
        for(int j=0; j<N; j++) {
            if (!visited[i][j]) {
                cur_ice = 0;
                find_ice_group(i, j);
                max_ice = max(max_ice, cur_ice);
            }
        }
    }

    if (max_ice < 2) max_ice = 0;

    string result;
    result += to_string(total_ice) + "\n";
    result += to_string(max_ice);

    return result;
}

int main() {

    init();

    string result = calc();
    cout << result << endl;

    return 0;
}
