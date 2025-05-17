#include <iostream>

using namespace std;

int calc(int n, int m) {

    string buf;
    getline(cin, buf, '\n');
    for(int i=0; i<m; i++) getline(cin, buf, '\n');

    return n-1;
}

int main() {

    int T;
    cin >> T;

    int N, M;
    string result = "";
    for(int i=0; i<T; i++) {
        cin >> N >> M;
        result += to_string(calc(N, M)) + '\n';
    }

    cout << result << endl;

    return 0;
}
