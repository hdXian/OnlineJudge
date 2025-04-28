#include <iostream>
#include <string>
#include <stack>

using namespace std;
static string exps = "()+-*/";
static int priors[] = {0, 0, 1, 1, 2, 2};

string calc(string exp) {
    stack<pair<char, int>> exp_stk;
    string result = "";

    int idx, p;
    // exp를 순회한다.
    for(char ch: exp) {
        // 피연산자는 그대로 출력
        if (isalpha(ch)) {
            result += ch;
        }
        // 연산자가 '('인 경우
        else if(ch == '(') {
            exp_stk.push(make_pair(ch, 0));
        }
        // 연산자가 ')'인 경우
        else if (ch == ')') {
            while(exp_stk.top().first != '(') {
                result += exp_stk.top().first;
                exp_stk.pop();
            }
            exp_stk.pop(); // 마지막 '('는 그냥 pop
        }
        else {
            idx = exps.find(ch);
            p = priors[idx]; // ch의 우선순위 가져오기

            // 현재 ch의 우선순위보다 크거나 같은 연산자들을 모두 pop해서 출력
            while(!exp_stk.empty() && exp_stk.top().second >= p) {
                result += exp_stk.top().first;
                exp_stk.pop();
            }

            // ch를 push
            exp_stk.push(make_pair(ch, p));
        }
        
    }

    // 스택에 남은 연산자 모두 pop
    while(!exp_stk.empty()) {
        result += exp_stk.top().first;
        exp_stk.pop();
    }

    return result;
}

int main() {
    string exp;
    cin >> exp;
    
    string result = calc(exp);
    cout << result << endl;

    return 0;
}
