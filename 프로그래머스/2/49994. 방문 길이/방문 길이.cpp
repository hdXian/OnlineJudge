#include <string>
#include <vector>
#include <iostream>

using namespace std;

char dir[4] = {'U', 'D', 'R', 'L'};
int dx[4] = {0, 0, 1, -1};
int dy[4] = {1, -1, 0, 0};

int solution(string dirs) {
    
    vector<vector<bool>> history(121, vector<bool>(4, false));
    
    // 각 좌표를 0~120의 번호로 표현한다.
    // 각 번호에서 U, D, R, L로 간 기록이 있는지 확인해서, 간 적이 없으면 카운트한다.
    int x = 0;
    int y = 0;
    int nx, ny;
    int node_idx, next_node_idx;
    int r_i;
    int count = 0;
    for(char ch: dirs) {
        
        for(int i=0; i<4; i++) {
            if (ch == dir[i]) {
                nx = x + dx[i];
                ny = y + dy[i];
                
                if (nx < -5 || ny < -5 || nx > 5 || ny > 5)
                    break;
                
                node_idx = (x+5) + ((5-y) * 11);
                next_node_idx = (nx+5) + ((5-ny) * 11);
                // U, R이면 인덱스+1로 D, L 만들기
                // 혹은 반대
                r_i = (i == 0 || i == 2) ? i+1 : i-1;
                
                if (history[node_idx][i] == false && history[next_node_idx][r_i] == false) {
                    count++;
                    history[node_idx][i] = true;
                    history[next_node_idx][r_i] = true;
                }
                
                x = nx;
                y = ny;
                
                break;
            }
        }
        
    }
    
    int answer = count;
    return answer;
}