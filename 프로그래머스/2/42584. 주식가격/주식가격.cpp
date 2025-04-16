#include <string>
#include <vector>
#include <stack>

using namespace std;

vector<int> solution(vector<int> prices) {
    int length = prices.size();
    vector<int> answer(length, 0);
    
    // 모노 스택 알고리즘
    stack<int> mono;
    int tmp;
    for(int i=0; i<length; i++) {
        tmp = prices[i];
        
        // 1. 살아남은 항목들을 카운트한다.
        // for(int idx: mono) answer[idx]++;
        
        // 2. 현재 주식 항목보다 가격이 높은 항목들을 스택에서 제거한다. (가격을 방어하지 못했다.)
        while (!mono.empty() && prices[mono.top()] > tmp) {
            // mono.top()의 생존 기간 = (현재 시점 - mono.top()) (mono.top()은 해당 주식의 매수 시점)
            answer[mono.top()] = i - mono.top();
            mono.pop();
        }
        
        // 3. 현재 항목을 스택에 추가한다.
        mono.push(i);
    }
    
    // 마지막까지 스택에 남은 
    while(!mono.empty()) {
        answer[mono.top()] = (length-1) - mono.top();
        mono.pop();
    }
    
    return answer;
}