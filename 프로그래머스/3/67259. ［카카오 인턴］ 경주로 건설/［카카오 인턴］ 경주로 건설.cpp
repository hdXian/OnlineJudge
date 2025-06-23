#include <string>
#include <vector>
#include <queue>

using namespace std;

#define INF 999999

int dr[] = {-1, 1, 0, 0};
int dc[] = {0, 0, -1, 1};

vector<vector<int>> board_ext;
int table[25][25][4]; // 상, 하, 좌, 우로부터 해당 좌표에 진입하는 경로의 최소 비용을 저장한다.
int N;

struct Node {
    int row, col;
    int dir; // 해당 노드에 방문하기 직전의 방향
    int cost;
    Node(int row, int col, int dir, int cost) {
        this->row = row;
        this->col = col;
        this->dir = dir;
        this->cost = cost;
    }
};

void bfs(int row, int col, int direction) {
    queue<Node> q;
    q.push(Node(row, col, direction, 0));
    
    while(!q.empty()) {
        Node cur = q.front();
        q.pop();
        
        int r = cur.row;
        int c = cur.col;
        int dir = cur.dir;
        int cost = cur.cost;
        
        for(int i=0; i<4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            // 상하좌우 노드를 방문
            if (nr >=0 && nc >=0 && nr < N && nc < N && board_ext[nr][nc] == 0) {
                // 방향이 변하지 않았을 경우 +100, 변했을 경우 +600
                // 상->하, 좌->우 같은 형식으로 바뀌는 경우는 제외 (역행)
                int new_cost = (dir == i) ? (cost+100) : (cost+600);
                
                // 더 짧은 거리가 있을 경우 큐에 추가
                if (new_cost < table[nr][nc][i]) {
                    table[nr][nc][i] = new_cost;
                    q.push(Node(nr, nc, i, new_cost));
                }
                
            }
        }
        
    }
    
}

int solution(vector<vector<int>> board) {
    
    N = board.size();
    board_ext = board;
    
    // 1. dp 테이블을 초기화한다.
    for(int i=0; i<25; i++) {
        for(int j=0; j<25; j++) {
            for(int k=0; k<4; k++)
                table[i][j][k] = INF;
        }
    }
    
    // 2. bfs로 시작지점 -> 우, 시작지점 -> 하 2가지 경우를 계산한다.
    // bfs 안에서 걸러도 되는데, 조건문을 너무 많이 비교할 것 같음.
    bfs(0, 0, 3);
    bfs(0, 0, 1);
    
    int result = table[N-1][N-1][0];
    for(int i=0; i<4; i++) result = min(result, table[N-1][N-1][i]);
    
    return result;
}

