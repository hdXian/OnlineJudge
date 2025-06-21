#include <string>
#include <vector>
#include <queue>

using namespace std;

int dr[] = {-1, 1, 0, 0};
int dc[] = {0, 0, -1, 1};

struct Node {
    int row, col, cost;
    Node(int row, int col, int cost) {
        this->row = row;
        this->col = col;
        this->cost = cost;
    }
};

// src -> dst까지의 최단거리를 bfs로 찾아 리턴하는 함수
int bfs(vector<string>& maps, pair<int, int> src, pair<int, int> dst) {
    int r_siz = maps.size();
    int c_siz = maps[0].length();
    vector<vector<bool>> visited(r_siz, vector<bool>(c_siz, false));
    
    queue<Node> q;
    q.push(Node(src.first, src.second, 0));
    visited[src.first][src.second] = true;
    
    Node result(-1, -1, -1);
    
    while(!q.empty()) {
        Node cur = q.front();
        q.pop();
        
        if(cur.row == dst.first && cur.col == dst.second) {
            result = cur;
            break;
        }
        
        for(int i=0; i<4; i++) {
            int nr = cur.row + dr[i];
            int nc = cur.col + dc[i];
            int next_cost = cur.cost + 1;
            if (nr >=0 && nc >=0 && nr < r_siz && nc < c_siz && maps[nr][nc] != 'X' && !visited[nr][nc]) {
                q.push(Node(nr, nc, next_cost));
                visited[nr][nc] = true;
            }
        }
        
    }
    
    return result.cost;
}

int solution(vector<string> maps) {
    // 1. 시작점 -> 레버까지의 최단거리를 구한다.
    // 2. 레버 -> 도착점까지의 최단거리를 구한다.
    pair<int, int> start;
    pair<int, int> middle;
    pair<int, int> end;
    
    int r_siz = maps.size();
    int c_siz = maps[0].length();
    for(int r=0; r<r_siz; r++) {
        for(int c=0; c<c_siz; c++) {
            if (maps[r][c] == 'O' || maps[r][c] == 'X') continue;
            
            if (maps[r][c] == 'S') {
                start.first = r;
                start.second = c;
            }
            else if (maps[r][c] == 'L') {
                middle.first = r;
                middle.second = c;
            }
            else {
                end.first = r;
                end.second = c;
            }
        }
    }
    
    int c1 = bfs(maps, start, middle);
    if (c1 == -1) return -1;
    
    int c2 = bfs(maps, middle, end);
    if (c2 == -1) return -1;
    
    return c1 + c2;
}