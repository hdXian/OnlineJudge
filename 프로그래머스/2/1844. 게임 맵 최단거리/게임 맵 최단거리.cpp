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

int bfs(vector<vector<int>>& maps) {
    int N = maps.size();
    int M = maps[0].size();
    
    vector<vector<bool>> visited(N, vector<bool>(M, false));
    
    queue<Node> q;
    q.push(Node(0, 0, 1));
    visited[0][0] = true;
    
    int result = -1;
    
    while(!q.empty()) {
        Node cur = q.front();
        q.pop();
        
        if (cur.row == N-1 && cur.col == M-1) {
            result = cur.cost;
            break;
        }
        
        for(int i=0; i<4; i++) {
            int nr = cur.row + dr[i];
            int nc = cur.col + dc[i];
            int next_cost = cur.cost + 1;
            if(nr >=0 && nc >=0 && nr < N && nc < M && maps[nr][nc] == 1 && !visited[nr][nc]) {
                q.push(Node(nr, nc, next_cost));
                visited[nr][nc] = true;
            }
        }
        
    }
    
    return result;
}

int solution(vector<vector<int>> maps) {
    
    int answer = bfs(maps);
    return answer;
}