#include <iostream>
#include <vector>

using namespace std;

static int N;

// 최대공약수. 유클리드 호제법.
long long gcd(long long a, long long b) {
    long long mod;
    while(b != 0) {
        mod = a % b;
        a = b;
        b = mod;
    }
    return a;
}

long long lcm(long long a, long long b) {
    return a * b / gcd(a, b);
}

long long calc(vector<int>& stakes) {

    if (stakes.size() < 2) return stakes[0]*2;

    // 0. 스테이크들의 시간을 2배로 늘린다. (짝수 번 굽는 횟수로 맞춘다)
    for(int i=0; i<N; i++) stakes[i] *= 2;

    // 1. 스테이크들의 최대공약수를 구한다.

    // 2. 최대공약수를 이용하여 최소공배수를 구한다.

    // 여러 수의 최소공배수 -> n1, n2의 최소공배수 c1을 구하고, 다시 c1과 n3의 최소공배수를 구하는 것을 반복한다.
    long long result = lcm(stakes[0], stakes[1]);
    for(int i=2; i<N; i++) {
        result = lcm(result, stakes[i]);
    }

    return result;
}

int main() {

    cin >> N;
    vector<int> stakes;
    int tmp;
    for(int i=0; i<N; i++) {
        cin >> tmp;
        stakes.push_back(tmp);
    }

    long long result = calc(stakes);
    cout << result << endl;

    return 0;
}
