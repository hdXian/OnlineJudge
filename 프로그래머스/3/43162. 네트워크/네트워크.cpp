#include <string>
#include <vector>

using namespace std;

#define MAX_SIZ 200
bool visited[MAX_SIZ] = {false, };
int N;

void dfs(int node, vector<vector<int>> computers) {
    visited[node] = true;
    
    // 이웃 노드들을 방문
    for(int i=0; i<N; i++) {
        if(!visited[i] && computers[node][i] == 1) {
            dfs(i, computers);
        }
    }
    
}

int solution(int n, vector<vector<int>> computers) {
    // n: 1 ~ 200
    // 컴퓨터는 0 ~ n-1 정수로 표현
    // 네트워크 개수 찾기
    
    N = n;
    // 1. visited 배열을 선언한다.
    // 2. 모든 노드들에 대해 dfs를 돌리고, 카운트한다.
    int count = 0;
    for(int i=0; i<N; i++) {
        if(!visited[i]) {
            dfs(i, computers);
            count++;
        }
    }
    
    return count;
}