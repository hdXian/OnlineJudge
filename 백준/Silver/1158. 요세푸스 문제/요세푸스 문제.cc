#include <iostream>
#include <string>
#include <queue>

using namespace std;

string calc(int n, int k) {
    queue<int> q;
    for(int i=1; i<=n; i++) q.push(i);

    vector<int> v;
    while(!q.empty()) {

        // k-1번 돌리기
        for(int i=0; i<k-1; i++) {
            int tmp  = q.front();
            q.pop();
            q.push(tmp);
        }

        // k번째에 있는 놈 뽑기
        v.push_back(q.front());
        q.pop();
    }

    string result = "<";
    for(int i: v) result += to_string(i) + ", ";
    result = result.substr(0, result.length()-2);
    result += ">";

    return result;
}

int main() {
    int N, K;
    cin >> N >> K;

    string result = calc(N, K);
    cout << result << endl;
    
    return 0;
}

