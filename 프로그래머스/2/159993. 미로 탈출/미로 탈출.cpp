#include <string>
#include <vector>
#include <queue>

using namespace std;

int r_siz;
int c_siz;

int dr[4] = {-1, 1, 0, 0};
int dc[4] = {0, 0, -1, 1};

struct Node {
    int row, col;
    int cost;
    Node(int row, int col, int cost) {
        this->row = row;
        this->col = col;
        this->cost = cost;
    }
};

int bfs(vector<string>& board, Node src, Node dst) {
    vector<vector<bool>> visited(r_siz, vector<bool>(c_siz));
    
    int cost = 0;
    queue<Node> q;
    visited[src.row][src.col] = true;
    q.push(Node(src.row, src.col, 0));
    Node result(-1, -1, -1);
    
    while(!q.empty()) {
        Node cur = q.front();
        q.pop();

        if (cur.row == dst.row && cur.col == dst.col) {
            result = cur;
            break;
        }

        for(int i=0; i<4; i++) {
            int nr = cur.row + dr[i];
            int nc = cur.col + dc[i];
            if (nr >= 0 && nc >= 0 && nr < r_siz && nc < c_siz && board[nr][nc] != 'X' && !visited[nr][nc]) {
                visited[nr][nc] = true;
                q.push(Node(nr, nc, cur.cost+1));
            }
        }
        
    }
    
    return result.cost;
}

int solution(vector<string> maps) {
    // 1. 출발지점 -> 레버 칸으로 이동하는 최단거리 구하기
    // 2. 레버 칸 -> 출구 칸으로 이동하는 최단거리 구하기
    // 3. 둘이 더하기
    
    int sr, sc; // 출발
    int er, ec; // 도착
    int lr, lc; // 레버
    
    // 출발, 도착, 레버 지점의 row, col 구하기
    r_siz = maps.size();
    c_siz = maps[0].size();
    for(int i=0; i<r_siz; i++) {
        for(int j=0; j<c_siz; j++) {
            if (maps[i][j] == 'O' || maps[i][j] == 'X') continue;
            if (maps[i][j] == 'S') {
                sr = i;
                sc = j;
            }
            else if (maps[i][j] == 'L') {
                lr = i;
                lc = j;
            }
            else {
                er = i;
                ec = j;
            }
        }
    }
    
    // 출발지 -> 레버
    int c1 = bfs(maps, Node(sr, sc, 0), Node(lr, lc, 0));
    
    // 레버 -> 도착지
    int c2 = bfs(maps, Node(lr, lc, 0), Node(er, ec, 0));
    
    int answer;
    if (c1 < 0 || c2 < 0) answer = -1;
    else answer = c1 + c2;
    
    return answer;
}