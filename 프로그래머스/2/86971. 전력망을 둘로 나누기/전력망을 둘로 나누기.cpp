#include <string>
#include <vector>
#include <iostream>
#include <algorithm>

using namespace std;

int N;
int result = 1000;

vector<bool> visited;
int tmp_count;

vector<vector<int>> neighbors;

void dfs(int n, vector<int>& pass_wire) {
    visited[n] = true;
    tmp_count++;
    
    for(int neighbor: neighbors[n]) {
        if (pass_wire[0] == n && pass_wire[1] == neighbor) continue;
        if(!visited[neighbor]) dfs(neighbor, pass_wire);
    }
    
}

void div(vector<int>& pass_wire) {
    visited = vector<bool>(N+1, false);
    tmp_count = 0;
    int r = pass_wire[0]; // 끊어진 간선 둘 중 하나를 루트로 잡아서 dfs를 돌려본다.
    dfs(r, pass_wire);
    result = min(result, abs(tmp_count - (N-tmp_count)));
}

int solution(int n, vector<vector<int>> wires) {
    N = n;
    
    // 인접 리스트 초기화
    neighbors = vector<vector<int>>(N+1);
    int n1, n2;
    for(const auto& wire: wires) {
        n1 = wire[0];
        n2 = wire[1];
        neighbors[n1].push_back(n2);
        neighbors[n2].push_back(n1);
    }
    
    // 1. 각 전력망을 하나씩 제거한 다음 루트노드부터 dfs를 돌려본다.
    // 2. (전체 노드 개수) - (dfs로 구한 노드 개수) = 나머지 한쪽 전력망의 노드 개수
    // 3. 둘의 차이가 가장 적은 경우를 저장하고 리턴한다.
    for(vector<int>& wire: wires) {
        div(wire);
    }
    
    return result;
}