#include <iostream>
#include <vector>

using namespace std;

int R, C, T;
vector<vector<int>> board;
vector<vector<int>> tmpBoard;

int up_r;
int up_c = 0;

int down_r;
int down_c = 0;

int dr[] = {-1, 1, 0, 0};
int dc[] = {0, 0, -1, 1};

int total_dust = 0;

void init() {

    cin >> R >> C >> T;

    // 보드 입력받기
    bool flag = true;
    board.resize(R);
    for(int i=0; i<R; i++) {
        board[i].resize(C);
        for(int j=0; j<C; j++) {
            cin >> board[i][j];
            total_dust += board[i][j];

            // 공기청정기 위치 입력받기
            if (board[i][j] == -1 && flag) {
                up_r = i;
                down_r = i+1;
                flag = false;
            }
        }
    }

    total_dust += 2; // 공기청정기(-1) 2개
}

void spread(int row, int col, vector<vector<int>>& tmpBoard) {
    if (board[row][col] < 5) return;

    int dust = board[row][col] / 5;

    for(int i=0; i<4; i++) {
        int nr = row + dr[i];
        int nc = col + dc[i];
        // 범위를 벗어나지 않고, 공기청정기 위치가 아니면
        if (nr >=0 && nc >=0 && nr < R && nc < C && board[nr][nc] != -1) {
            tmpBoard[nr][nc] += dust;
            tmpBoard[row][col] -= dust;
        }
    }

}

void spreadDust() {
    vector<vector<int>> tmpBoard(R, vector<int>(C, 0));

    // 먼지는 동시에 확산되므로 먼지 변화량을 tmpBoard에 저장한다.
    for(int i=0; i<R; i++) {
        for(int j=0; j<C; j++) {
            spread(i, j, tmpBoard);
        }
    }

    // 먼지 변화량을 board에 반영한다.
    for(int i=0; i<R; i++) {
        for(int j=0; j<C; j++) {
            board[i][j] += tmpBoard[i][j];
        }
    }

}

void circlue(int start_r, int start_c, bool is_clock) {
    // 시계방향 공기순환 -> 배열 값 업데이트는 반시계 방향으로
    if (is_clock) {
        total_dust -= board[start_r+1][start_c];
        for(int r=start_r+1; r < R-1; r++) {
            board[r][start_c] = board[r+1][start_c];
        }
        for(int c=start_c; c<C-1; c++) {
            board[R-1][c] = board[R-1][c+1];
        }
        for(int r=R-1; r>start_r; r--) {
            board[r][C-1] = board[r-1][C-1];
        }
        for(int c=C-1; c>start_c+1; c--) {
            board[start_r][c] = board[start_r][c-1];
        }
        board[start_r][start_c+1] = 0;
    }
    // 반시계방향 공기순환 -> 배열 값 업데이트는 시계방향으로
    else {
        total_dust -= board[start_r-1][start_c];
        for(int r=start_r-1; r>0; r--) {
            board[r][start_c] = board[r-1][start_c];
        }
        for(int c=0; c<C-1; c++) {
            board[0][c] = board[0][c+1];
        }
        for(int r=0; r<start_r; r++) {
            board[r][C-1] = board[r+1][C-1];
        }
        for(int c=C-1; c>start_c+1; c--) {
            board[start_r][c] = board[start_r][c-1];
        }
        board[start_r][start_c+1] = 0;
    }

}

void air_circule() {
    // 공기청정기 위쪽은 반시계방향
    circlue(up_r, up_c, false);

    // 공기청정기 아래쪽은 시계방향
    circlue(down_r, down_c, true);
}

void calc() {
    // 아래 동작을 T번 반복한다.
    for(int i=0; i<T; i++) {
        // 1. 미세먼지가 확산된다.
        spreadDust();
        // 2. 공기청정기가 가동된다.
        air_circule();
    }
}

int main() {
    init();
    calc();
    cout << total_dust << endl;

    return 0;
}
