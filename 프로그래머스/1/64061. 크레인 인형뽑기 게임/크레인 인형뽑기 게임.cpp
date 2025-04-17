#include <string>
#include <vector>
#include <stack>

using namespace std;

int solution(vector<vector<int>> board, vector<int> moves) {
    int N = board.size(); // 격자 크기
    
    // 1. 스택 vector를 선언한다.
    vector<stack<int>> sts(N);
    
    // 2. 아래부터 순회하면서 각 스택을 채운다.
    vector<int> line;
    for(int i=N-1; i>=0; i--) {
        line = board[i];
        for(int j=0; j<N; j++) {
            if (line[j] != 0)
                sts[j].push(line[j]);
        }
    }
    
    // 3. moves에 대해 시뮬레이션을 돌린다. 
    stack<int> bucket;
    int tmp, idx;
    int count = 0;
    for(int m: moves) {
        idx = m-1;
        if (!sts[idx].empty()) { // 해당 스택이 비지 않았다면
            // 해당 스택의 top와 바구니의 top를 비교
            tmp = sts[idx].top();
            
            if (!bucket.empty() && (tmp == bucket.top())) { // 둘이 같으면 인형을 제거 (바구니에서도 pop)
                bucket.pop();
                count += 2;
            }
            else { // 둘이 다르다면 선택한 인형을 바구니에 push
                bucket.push(tmp);
            }
            sts[idx].pop(); // 해당 스택은 항상 pop
        }
        // 해당 스택이 비었다면 아무것도 하지 않음
    }
    
    int answer = count;
    return answer;
}