#include <string>
#include <vector>

#include <stack>
#include <queue>

using namespace std;

char chs[6] = {'[', '{', '(', ')', '}', ']'};

int convert(char ch) {
    for(int i=0; i<6; i++) {
        if (ch == chs[i])
            return i;
    }
    return 0;
}

bool check(deque<char> &dq) {
    stack<int> st;
    int tmp;
    for(char ch: dq) {
        tmp = convert(ch);
        if (tmp < 3) {
            st.push(tmp);
        }
        else {
            if (st.empty()) return false; // 스택 비었으면 false
            if (tmp + st.top() != 5) return false; // 괄호 안 맞으면 false
            st.pop();
        }
    }
    
    return st.empty(); // 문자열 순회 후 스택에 남은게 없다면 true
}

int solution(string s) {
    deque<char> dq;
    for(char ch: s)
        dq.push_back(ch);
    
    int count = 0;
    int length = s.length();
    
    if (check(dq)) count++;
    
    char tmp;
    for(int i=1; i<length; i++) {
        tmp = dq.front();
        dq.pop_front();
        dq.push_back(tmp);
        if (check(dq)) count++;
    }
    
    int answer = count;
    return answer;
}

