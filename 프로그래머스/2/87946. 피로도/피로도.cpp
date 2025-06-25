#include <string>
#include <vector>
#include <iostream>

using namespace std;

int result;
int N;
vector<bool> visited;
vector<vector<int>> ds;

void dfs(int cur_hp, int count) {
    result = max(result, count);
    
    int required, cost;
    for(int i=0; i<N; i++) {
        required = ds[i][0];
        cost = ds[i][1];
        
        if (visited[i] || cur_hp < required) continue;

        visited[i] = true;
        dfs(cur_hp - cost, count+1);
        visited[i] = false;
    }
    
}

int solution(int k, vector<vector<int>> dungeons) {
    // k: 1 ~ 5천
    // 던전 길이: 1 ~ 8, [최소 피로도, 소모 피로도], 최 >= 소, 1 ~ 1천, 서로 다른 던전끼리 다를 수 있음.
    // 탐험할 수 있는 최대 던전 수
    N = dungeons.size();
    result = 0;
    ds = dungeons;
    
    // 1. visited 배열을 선언한다.
    visited = vector<bool>(N, false);
    
    // 2. dfs로 던전들을 방문하는 모든 경우의 수를 계산한다.
    // 3. 가장 던전을 많이 방문할 수 있는 경우를 저장해 리턴한다.
    dfs(k, 0);
    
    return result;
}