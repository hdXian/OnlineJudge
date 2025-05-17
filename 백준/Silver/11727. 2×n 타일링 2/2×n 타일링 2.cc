#include <iostream>

using namespace std;

int calc(int n) {
    if (n == 1) return 1;
    if (n == 2) return 3;

    int p = 10007;

    int table[1001];
    table[1] = 1;
    table[2] = 3;

    for(int i=3; i<=n; i++) {
        table[i] = (table[i-1] + table[i-2]*2) % p;
    }

    return table[n];
}

int main() {
    int n;
    cin >> n;

    int result = calc(n);
    cout << result << endl;
    return 0;
}
