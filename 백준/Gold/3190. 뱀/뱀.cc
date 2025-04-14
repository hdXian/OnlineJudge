#include <iostream>
#include <queue>
#include <algorithm>

using namespace std;

static int N, K;

bool board[101][101] = {false, };
bool apple[101][101] = {false, };

bool cmd_compare(pair<int, char> &p1, pair<int, char> &p2) {
    // 시간(first) 기준으로 오름차순
    return p1.first < p2.first;
}

deque<pair<int, char>> cmds;

// 상, 우, 하, 좌 (시계방향)
int dr[4] = {-1, 0, 1, 0};
int dc[4] = {0, 1, 0, -1};

void init() {
    cin >> N; // 보드 크기. 2 ~ 100
    cin >> K; // 사과 개수. 0 ~ 100
    int r, c;

    // 사과 입력받기
    for(int i=0; i<K; i++) {
        cin >> r >> c;
        apple[r][c] = true;
    }

    // 명령어 입력 (시간, 명령어)
    int L;
    cin >> L;
    int time;
    char cmd;
    for(int i=0; i<L; i++) {
        cin >> time; // 명령어 입력 시간. 1 ~ 1만
        cin >> cmd; // 명령어. 'L' or 'D'
        cmds.push_back(make_pair(time, cmd));
    }

    // 명령어 큐를 시간 (first) 기준으로 오름차순 정렬
    sort(cmds.begin(), cmds.end(), cmd_compare);
}

// 방향 전환
int turn(int curDir, char cmd) {
    if (cmd == 'L') return (curDir + 3) % 4; // 왼쪽 회전
    else return (curDir + 1) % 4; // 오른쪽 회전
}

int calc() {
    // 머리의 현재 위치를 나타내는 row, col
    int row = 1;
    int col = 1;
    int curDir = 1; // 현재 방향을 나타내는 변수
    int t = 0; // 게임 경과 시간을 나타낼 변수

    // pair<int, int>를 가지는 deque 자료구조에 뱀을 저장
    deque<pair<int, int>> snake;
    snake.push_front(make_pair(row, col));
    board[1][1] = true; // 시작점은 1, 1

    // 다음 명령어가 입력되는 시간과 명령어를 저장
    int next_t = -1;
    char next_cmd = 'L';
    if (!cmds.empty()) {
        next_t = cmds.front().first;
        next_cmd = cmds.front().second;
        cmds.pop_front();
    }

    int nr, nc;
    pair<int, int> tail;
    while (true) {
        t++;

        nr = row + dr[curDir];
        nc = col + dc[curDir];

        if (nr < 1 || nc < 1 || nr > N || nc > N) break; // 범위 벗어나면 게임 종료
        if (board[nr][nc]) break; // 자신의 몸과 부딪히면 종료

        // 머가리 이동
        snake.push_front(make_pair(nr, nc));
        board[nr][nc] = true;

        // 사과가 있을 경우: 해당 사과를 없애고 꼬리를 그대로 놔둠
        if (apple[nr][nc]) {
            apple[nr][nc] = false;
        }
        // 사과가 없을 경우: 꼬리를 제거
        else {
            tail = snake.back();
            board[tail.first][tail.second] = false;
            snake.pop_back();
        }

        // 방향 전환 명령어가 있을 경우
        if (t == next_t) {
            curDir = turn(curDir, next_cmd);
            if (cmds.empty()) {
                next_t = -1;
            }
            else {
                next_t = cmds.front().first;
                next_cmd = cmds.front().second;
                cmds.pop_front();
            }
        }
        
        row = nr;
        col = nc;
    }

    return t;
}

int main() {

    init();

    int result = calc();
    cout << result << endl;

    return 0;
}
