#include <string>
#include <vector>
#include <iostream>

using namespace std;

vector<int> info;
int N;
vector<int> cur(11, 0);
vector<int> result;
int max_diff;

int calc_diff() {
    int s1 = 0;
    int s2 = 0;
    for(int i=0; i<=10; i++) {
        if (cur[i] == 0 && info[i] == 0) continue;
        if (cur[i] > info[i]) s1 += (10-i);
        else s2 += (10-i);
    }
    
    return s1 - s2;
}

void dfs(int cur_target, int remains) {
    // 모든 과녁에 화살을 다 쐈거나 남은 화살이 없다면
    if (cur_target < 0 || remains == 0) {
        cur[10] = remains;
        
        int diff = calc_diff();
        if (diff >  0 && diff > max_diff) {
            max_diff = diff;
            result = cur;
        }
        
        cur[10] = 0;
        return;
    }
    
    // 어피치가 쏜 과녁에 맞불로 쏠 경우 -> 어치피가 쏜 것보다 1개 더 많이 쏘는 것이 최선.
    if (remains > info[cur_target]) {
        int shoot = info[cur_target]+1;
        cur[cur_target] = shoot;
        
        dfs(cur_target-1, remains-shoot);
        
        cur[cur_target] = 0;
    }
    
    // 현재 과녁에 화살을 쏘지 않고 넘어갈 경우
    dfs(cur_target-1, remains);
}

vector<int> solution(int n, vector<int> _info) {
    N = n;
    info = _info;
    max_diff = -1;
    
    dfs(10, N);
    
    if (max_diff == -1) return vector<int>(1, -1);
    else return result;
}

