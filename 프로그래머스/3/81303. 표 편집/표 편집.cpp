#include <string>
#include <vector>
#include <stack>

using namespace std;

stack<int> del_history;

// 현재 커서 위치, 명령어
void exec_cmd(int& n, int& cur, vector<pair<int, int>>& ud, string cmd) {
    
    int move;
    int up_idx, down_idx;
    if (cmd[0] == 'U') {
        move = stoi(cmd.substr(2));
        for(int i=0; i<move; i++)
            cur = ud[cur].first;
    }
    else if (cmd[0] == 'D') {
        move = stoi(cmd.substr(2));
        for(int i=0; i<move; i++)
            cur = ud[cur].second;
    }
    else if (cmd[0] == 'C') {
        del_history.push(cur);
        up_idx = ud[cur].first;
        down_idx = ud[cur].second;
        
        // 삭제할 행 바로 위쪽의 down을 삭제할 행의 down으로 교체 (연결리스트와 유사)
        ud[up_idx].second = down_idx;
        
        // 아래 행의 up을 현재 행의 up으로 업데이트
        ud[down_idx].first = up_idx;
        cur = (down_idx > n) ? up_idx : down_idx;
    }
    else { // "R"
        int restore = del_history.top();
        
        up_idx = ud[restore].first;
        down_idx = ud[restore].second;
        
        ud[up_idx].second = restore;
        
        ud[down_idx].first = restore;
        
        del_history.pop();
    }
    
    
}

string solution(int n, int k, vector<string> cmd) {
    // 처음 표의 행 개수 n (5 ~ 100만)
    // 처음 선택된 행의 위치 k (0 <= k < n)
    // 명령어들 cmd (1 ~ 20만개 명령어) (X: 1 ~ 30만, 0으로 시작 x, 모든 X를 합친게 100만 이하)
    
    int cur = k+1; // 인덱스를 1씩 더해서 생각한다. (위아래 여유 행 때문)
    
    
    // 위아래에 여유 행을 하나씩 더 둔다.
    vector<pair<int, int>> ud(n+2); // pair<up, down>
    for(int i=0; i<n+2; i++) {
        ud[i].first = i-1; // up
        ud[i].second = i+1; // down
    }
    
    for(string s: cmd) {
        exec_cmd(n, cur, ud, s);
    }

    string answer;
    answer.append(n, 'O');
    int idx;
    while (!del_history.empty()) {
        idx = del_history.top();
        answer[idx-1] = 'X';
        del_history.pop();
    }
    return answer;
}
