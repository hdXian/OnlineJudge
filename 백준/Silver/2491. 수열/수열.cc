#include <iostream>
#include <vector>

using namespace std;

int calc(int n, vector<int>& seq) {

    int asc_max = 1;
    int desc_max = 1;

    int asc_count = 1;
    int desc_count = 1;

    // 수열을 순회하면서 연속 증가, 감소 수열을 찾는다.
    int cur, prev;
    for(int i=1; i<n; i++) {
        cur = seq[i];
        prev = seq[i-1];
        
        if (cur == prev) {
            asc_count++;
            desc_count++;
        }
        // 증가한 경우 증가 수열 카운트 증가, 감소 수열 카운트 초기화
        else if (cur > prev) {
            asc_count++;
            desc_max = max(desc_max, desc_count);
            desc_count = 1;
        }
        // 감소한 경우 감소 수열 카운트 증가, 증가 수열 카운트 초기화
        else {
            desc_count++;
            asc_max = max(asc_max, asc_count);
            asc_count = 1;
        }

    }

    asc_max = max(asc_max, asc_count);
    desc_max = max(desc_max, desc_count);

    return max(asc_max, desc_max);
}

int main() {
    int N; // 수열 길이. 1 ~ 10만
    cin >> N;

    vector<int> seq;
    int tmp;
    for(int i=0; i<N; i++) {
        cin >> tmp;
        seq.push_back(tmp);
    }

    int result = calc(N, seq);

    cout << result << endl;

    return 0;
}
