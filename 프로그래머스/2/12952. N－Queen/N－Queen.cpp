#include <string>
#include <vector>
#include <algorithm>

using namespace std;

int result = 0;
int N;

int dr[] = {-1, -1, 0, 1, 1, 1, 0, -1};
int dc[] = {0, 1, 1, 1, 0, -1, -1, -1};

// 기존 좌표, 새로 놓을 좌표
bool put_queen(vector<int>& cur, int r, int c) {
    int r_siz = cur.size();
    
    for(int i=0; i<r_siz; i++) {
        int cr = i;
        int cc = cur[i];
        
        // 같은 열이나 행에 있으면 배치 불가
        if(cr == r || cc == c) return false;
        
        // 놓는 위치에서 대각선 방향 중에 cr, cc로 지정한 퀸이 있으면 배치 불가
        for(int i=0; i<8; i++) {
            int nr = cr + dr[i];
            int nc = cc + dc[i];
            while(nr >=0 && nc >=0 && nr < N && nc < N) {
                if(nr == r && nc == c) return false;
                nr += dr[i];
                nc += dc[i];
            }
        }
    }
    
    return true;
}

void dfs(vector<int> cur) {
    if (cur.size() == N) {
        result++;
        return;
    }
    
    for(int i=0; i<N; i++) {
        int r = cur.size();
        int c = i;
        if (put_queen(cur, r, c)) {
            cur.push_back(i);
            dfs(cur);
            cur.pop_back();
        }
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