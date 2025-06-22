#include <string>
#include <vector>
#include <algorithm>

using namespace std;

vector<vector<int>> neighbors;
vector<bool> visited;

int result = 0;

// routes: 현재까지 방문한 노드들
void dfs(vector<int> routes, vector<int>& info) {
    // 현재까지 거쳐온 경로들의 늑대와 양의 수를 센다.
    int lambs = 0;
    int wolves = 0;
    for(int r: routes) {
        if (info[r] == 0) lambs++;
        else wolves++;
    }
    
    // 늑대가 많거나 같으면 양을 모두 잃는다. 탐색을 중단한다.
    if (wolves >= lambs) return;
    
    // 현재까지 구한 양의 개수를 비교해 저장한다.
    result = max(result, lambs);
    
    // 인접노드들 중 방문하지 않는 노드들에 대해 dfs를 수행한다.
    for(int node: routes) {
        // int node = routes[i];
        // 인접 노드들에 대해
        for(int neighbor: neighbors[node]) {
            if (visited[neighbor]) continue;
            
            // 인접 노드를 방문하는 경우의 수에 대해 계산해본다.
            visited[neighbor] = true;
            vector<int> next_routes(routes);
            next_routes.push_back(neighbor);
            // routes.push_back(neighbor);

            dfs(next_routes, info);

            // 인접 노드를 방문하지 않는 경우로 백트래킹한다.
            // routes.pop_back();
            visited[neighbor] = false;
        }
    }
    
}

int solution(vector<int> info, vector<vector<int>> edges) {
    // 가능한 모든 탐색 루트로 탐색해서 양을 가장 많이 데려올 수 있는 경우를 탐색한다.
    int N = info.size();
    
    // 1. 인접 리스트 배열을 초기화한다.
    neighbors.resize(N);
    int src, dst;
    for(vector<int>& edge: edges) {
        src = edge[0];
        dst = edge[1];
        neighbors[src].push_back(dst);
    }
    
    // 2. 방문 배열을 초기화한다.
    visited = vector<bool>(N, false);
    visited[0] = true;
    
    // 3. dfs로 모든 그래프 경로를 탐색하면서 데려올 수 있는 양의 최대 개수를 계산한다.
    vector<int> routes;
    routes.push_back(0);
    dfs(routes, info);
    
    return result;
}