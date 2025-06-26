#include <string>
#include <vector>
#include <algorithm>

#include <iostream>

using namespace std;

int result = 0;
int N;

int dr[] = {-1, -1, 0, 1, 1, 1, 0, -1};
int dc[] = {0, 1, 1, 1, 0, -1, -1, -1};

bool put_queen(vector<int>& cur) {
    vector<vector<bool>> board(N, vector<bool>(N, false));
    int r_siz = cur.size();
    
    for(int i=0; i<r_siz; i++) {
        int row = i;
        int col = cur[i];
        
        // 퀸을 두려는 자리가 배치 불가능한 위치할 경우 false 리턴
        if (board[row][col]) return false; 
        
        board[row][col] = true;
        // 8방향으로 뻗어나가며 배치 불가능한 영역 업데이트
        for(int k=0; k<8; k++) {
            int nr = row + dr[k];
            int nc = col + dc[k];
            while(nr >=0 && nc >=0 && nr < N && nc < N) {
                board[nr][nc] = true;
                nr += dr[k];
                nc += dc[k];
            }
        }
    }
    
    // 문제없이 배치되었다면 true 리턴
    return true;
}

void dfs(vector<int> cur) {
    if (cur.size() == N) {
        result++;
        return;
    }
    
    for(int i=0; i<N; i++) {
        if (find(cur.begin(), cur.end(), i) != cur.end()) continue;
        
        cur.push_back(i);
        if (put_queen(cur)) dfs(cur);
        cur.pop_back();
    }
    
}

int solution(int n) {
    N = n;
    // 1. N*N 위치에 퀸을 한번씩 배치해본다.
    // 2. 퀸을 배치한 다음 배치가 불가능한 위치를 표시하고, 다음 배치 가능한 위치에 퀸을 배치한다.
    // 3. 모든 퀸을 성공적으로 배치했다면 카운트를 1 증가시키고, 불가능하다면 백트래킹한다.
    vector<int> cur;
    dfs(cur);
    
    return result;
}