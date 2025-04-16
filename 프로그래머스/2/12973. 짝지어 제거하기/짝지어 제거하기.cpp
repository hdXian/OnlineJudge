#include <iostream>
#include <string>
#include <stack>
using namespace std;

bool check(string s) {
    stack<char> st;
    
    for(char ch: s) {
        if (st.empty() || (st.top() != ch)) st.push(ch);
        else st.pop();
    }
    
    return st.empty();
}

int solution(string s)
{
    bool result = check(s);
    int answer = result;

    return answer;
}