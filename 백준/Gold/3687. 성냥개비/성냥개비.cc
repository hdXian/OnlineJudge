#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

static int K;
static vector<int> cases;
static string dp_table[101] = {"", }; // 인덱스 1부터 쓰기

string calc(int n) {
    string result = "";

    // 가장 작은 수 만들기
    result += (dp_table[n] + " ");

    // 가장 큰 수 만들기
    int twos = n / 2;
    int three = 0;
    if (n%2 == 1) {
        twos--;
        three++;
    }
    for(int i=0; i<three; i++) result += "7";
    for(int i=0; i<twos; i++) result += "1";

    return result;
}

string move_zeros(string s) {
    int seq = 0;
    while(seq < s.length() && s[seq] == '0') seq++;

    // c++의 substr() 2번째 인자는 자를 문자의 개수임.
    if (seq == s.length()) {
        s.replace(0, 1, "6"); // 0번 인덱스부터 1글자까지의 범위를 "6"으로 교체
        return s;
    }
    else {
        return s[seq] + s.substr(0, seq) + s.substr(seq+1);
    }
    
}

void make_table() {
    // 가장 작은 수 dp 테이블 채우기
    string s = "174208";
    for(int i=2; i<=7; i++) {
        dp_table[i] = s[i-2];
    }
    dp_table[8] = "10";

    string cur, tmp;
    for(int i=9; i<=100; i++) {
        // 우선 dp[7]과 조합부터 비교
        cur = dp_table[7] + dp_table[i-7];
        sort(cur.begin(), cur.end()); // 문자열 정렬
        if(cur[0] == '0') cur = move_zeros(cur);

        // 6부터 비교
        for(int k=6; k>=1; k--) {
            if (k == 1) tmp = "6" + dp_table[i-6];
            else tmp = dp_table[k] + dp_table[i-k];

            if (tmp.length() > cur.length()) continue; // 자릿수가 더 많으면 무조건 더 큰수임. 건너 뜀.

            sort(tmp.begin(), tmp.end()); // 문자열 사전순 정렬
            if (tmp[0] == '0') tmp = move_zeros(tmp); // 0 뒤로 보내기

            //if (tmp.length() < cur.length()) {
            //    cur = tmp;
            //    continue;
            //}

            cur = (cur < tmp) ? cur : tmp;

        }
        dp_table[i] = cur;
    }

    dp_table[6] = "6";
}

void init() {
    cin >> K;
    cases.assign(K, 0);

    int n;
    for(int i=0; i<K; i++) {
        cin >> n;
        cases[i] = n;
    }
}

int main() {

    init();
    make_table();

    string result = "";
    for(int n: cases) {
        result += calc(n) + "\n";
    }
    cout << result << endl;

    return 0;
}

