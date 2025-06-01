#include <iostream>

using namespace std;

bool board[10][10] = {false, };
int N;
int total_score = 0;

void move_block(int t, int row, int col) {
    int blue_r, blue_c;
    int green_r, green_c;

    blue_r = green_r = row;
    blue_c = green_c = col;

    // 한칸짜리 블록
    if (t == 1) {
        // 파랑 처리
        while(blue_c < 10 && !board[blue_r][blue_c]) blue_c++;
        blue_c--;
        board[blue_r][blue_c] = true;

        // 초록 처리
        while(green_r < 10 && !board[green_r][green_c]) green_r++;
        green_r--;
        board[green_r][green_c] = true;
    }
    // 가로로 긴 블록
    else if (t == 2) {
        // 파랑 처리
        while(blue_c+1 < 10 && !board[blue_r][blue_c+1]) blue_c++;
        blue_c--;
        board[blue_r][blue_c] = true;
        board[blue_r][blue_c+1] = true;

        // 초록 처리
        while(green_r < 10 && !board[green_r][green_c] && !board[green_r][green_c+1]) green_r++;
        green_r--;
        board[green_r][green_c] = true;
        board[green_r][green_c+1] = true;
    }
    // 세로로 긴 블록
    else {
        // 파랑 처리
        while(blue_c < 10 && !board[blue_r][blue_c] && !board[blue_r+1][blue_c]) blue_c++;
        blue_c--;
        board[blue_r][blue_c] = true;
        board[blue_r+1][blue_c] = true;
        
        // 초록 처리
        while(green_r+1 < 10 && !board[green_r+1][green_c]) green_r++;
        green_r--;
        board[green_r][green_c] = true;
        board[green_r+1][green_c] = true;
    }

}

// 해당 열이 꽉 차있는지
bool is_col_filled(int col) {
    for(int r=0; r<4; r++) {
        if (!board[r][col]) return false;
    }

    return true;
}

// 해당 열이 한 칸이라도 차있는지 -> 연한 파랑 영역 확인용
bool check_col_edge(int col) {
    for(int r=0; r<4; r++) {
        if (board[r][col]) return true;
    }
    return false;
}

// 지정한 열을 제거하고 오른쪽으로 하나씩 이동하는 함수
void remove_col(int col) {
    while(col >= 4) {
        for(int r=0; r<4; r++) {
            board[r][col] = board[r][col-1];
        }
        col--;
    }
}

void remove_blue_block() {
    // 1. 파랑 영역의 블록을 제거한다.
    for(int col=9; col>=6; col--) {
        while(is_col_filled(col)) {
            total_score++; // 점수 적립
            remove_col(col);
        }
    }
    
    // 2. 연한 파랑 영역을 처리한다.
    int remove_count = 0;
    for(int col=4; col<=5; col++) {
        if (check_col_edge(col)) remove_count++;
    }

    for(int i=0; i<remove_count; i++) remove_col(9);
    
}

// 해당 열이 꽉 차있는지
bool is_row_filled(int row) {
    for(int c=0; c<4; c++) {
        if (!board[row][c]) return false;
    }

    return true;
}

// 해당 열이 한 칸이라도 차있는지 -> 연한 파랑 영역 확인용
bool check_row_edge(int row) {
    for(int c=0; c<4; c++) {
        if (board[row][c]) return true;
    }
    return false;
}

// 지정한 열을 제거하고 오른쪽으로 하나씩 이동하는 함수
void remove_row(int row) {
    while(row >= 4) {
        for(int c=0; c<4; c++) {
            board[row][c] = board[row-1][c];
        }
        row--;
    }
}

void remove_green_block() {
    // 1. 초록 영역의 블록을 제거한다.
    for(int row=9; row>=6; row--) {
        while(is_row_filled(row)) {
            total_score++; // 점수 적립
            remove_row(row);
        }
    }
    
    // 2. 연한 초록 영역을 처리한다.
    int remove_count = 0;
    for(int row=4; row<=5; row++) {
        if (check_row_edge(row)) remove_count++;
    }

    for(int i=0; i<remove_count; i++) remove_row(9);
}

int count_block() {
    int cnt = 0;
    for(int i=0; i<10; i++) {
        for(int j=0; j<10; j++) {
            if (board[i][j]) cnt++;
        }
    }

    return cnt;
}

void calc(int t, int x, int y) {
    // 1. 블록을 놓는다.
    move_block(t, x, y);

    // 2. 파랑 영역을 처리한다.
    remove_blue_block();

    // 3. 초록 영역을 처리한다.
    remove_green_block();
}

int main() {

    cin >> N;

    int t, x, y;
    for(int i=0; i<N; i++) {
        cin >> t >> x >> y;
        calc(t, x, y);
    }

    // 점수와 남은 블록 개수를 카운트한다.
    string result = "";
    result += to_string(total_score) + "\n";
    result += to_string(count_block());

    cout << result << endl;

    return 0;
}
