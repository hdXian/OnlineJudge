#include <string>
#include <vector>
#include <algorithm>
#include <queue>

#include <iostream>

using namespace std;

#define INF 999

vector<bool> visited;

vector<int> dist;
vector<int> weak;
int people_siz;
int total_weak;
int N;

int result = INF;

// start번째 취약 지점을 시작으로 했을 때, cur 순열 배치로 모든 취약지점을 커버하려면 최소 몇 명이 필요한지 리턴
int count_people(vector<int>& cur, int start) {
    // start번째 취약 지점부터 점검 시작
    // 이 때, 최소 몇 명의 사람이 필요한지를 리턴
    // 시작 지점에 첫 번째 사람을 배치하고, 그 사람이 점검 가능한 지점들을 목록에서 제거하고, ...
    // 전부 다 지워지거나 사람이 부족할 때까지 반복
    queue<int> q;
    for(int w: weak) q.push(w);
    for(int i=0; i<start; i++) {
        q.push(q.front());
        q.pop();
    }
    
    // 점검에 투입된 사람 수이자 cur 순열의 인덱스
    int count = 0;
    
    // 체크된 취약 지점 개수 카운트. weak 길이를 2배로 늘려놔서 따로 카운트한 뒤 종료 조건으로 활용
    int checked_weak = 0; 
    
    while(!q.empty()) {
        int pos = q.front();
        q.pop();
        checked_weak++;
        
        int p = cur[count]; // 사람 번호
        
        int reachable = pos + dist[p]; // 닿을 수 있는 거리
        // p번 사람이 점검 가능한 취약점들을 카운트
        while(!q.empty() && q.front() <= reachable) {
            q.pop();
            checked_weak++;
        }
        
        if (count >= people_siz || checked_weak >= total_weak) break;
        
        count++;
    }
    
    int return_val;
    if (!q.empty() && count == people_siz) return_val = INF;
    else return_val = (count+1); // count는 인덱스기 때문에 인원 수를 리턴하려면 +1로 리턴
    
    // cout << "return val: " << return_val << endl;
    return return_val;
}

// 각 지점을 시작지점으로 했을 때 가장 적은 사람이 투입되는 경우를 리턴
void check(vector<int>& cur) {
    // weak의 각 지점을 시작 지점으로 했을 때, cur 배치로 모든 취약 부분을 점검할 수 있는지 확인한다.
    // total_weak는 취약 지점의 개수. weak[]의 인덱스로 쓸 수 있다.
    for(int start=0; start<total_weak; start++) {
        int tmp = count_people(cur, start);
        result = min(result, tmp);
    }
}

// 순열 구하는 dfs. 순열을 다 구하면, 해당 순열로 사람을 배치해 가장 적은 인원으로 배치 가능한 경우를 업데이트
void dfs(vector<int> cur, int depth) {
    if (depth == people_siz) {
        check(cur);
        return;
    }
    
    for(int i=0; i<people_siz; i++) {
        if (visited[i]) continue;
        
        cur.push_back(i);
        visited[i] = true;
        
        dfs(cur, depth+1);
        cur.pop_back();
        visited[i] = false;
    }
}

int solution(int n, vector<int> _weak, vector<int> _dist) {
    // 1. 친구를 배치하는 모든 가능한 수를 순열로 구한다.
    // 2. 시계, 혹은 반시계 방향 중 한 쪽을 정해 친구들을 배치한다.
    // 2-1. 방향을 한 쪽만 생각해도 모든 경우의 수를 고려할 수 있다.
    // 이동 거리가 1인 친구가 1->2로 도는 것과 2->1로 도는 것은 같은 경우이기 때문
    N = n;
    weak = _weak;
    
    // 원래 약점 개수
    total_weak = _weak.size();
    
    // 길이 두 배로 늘리기
    for(int w: _weak) {
        weak.push_back(w + N);
    }
    
    dist = _dist;
    people_siz = dist.size();
    visited = vector<bool>(people_siz, false);
    
    vector<int> cur;
    dfs(cur, 0);
    
    if (result == 999) return -1;
    else return result;
}