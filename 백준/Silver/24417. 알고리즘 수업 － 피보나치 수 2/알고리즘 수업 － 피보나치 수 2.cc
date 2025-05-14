#include <iostream>

using namespace std;

const int P = 1000000007;

string calc(int n) {
    // 재귀 호출 실행 횟수
    // fibo(n)의 호출 횟수는 fibo(n-1) + fibo(n-2)
    int prev1 = 1;
    int prev2 = 1;

    int recur_result = 0;
    for(int i=0; i<n-2; i++) {
        recur_result = (prev1 + prev2) % P;
        prev2 = prev1;
        prev1 = recur_result;
    }

    string result = to_string(recur_result) + " " + to_string(n-2);

    return result;
}

int main() {
    int N; // N의 피보나치 수. 5 ~ 2억
    cin >> N;

    string result = calc(N);
    cout << result << endl;

    return 0;
}
