#include <iostream>
#include <vector>
#include <unordered_map>
#include <algorithm>

using namespace std;

unordered_map<string, int> word_map;

bool word_comp(string s1, string s2) {
    int c1 = word_map[s1];
    int c2 = word_map[s2];
    if (c1 == c2) {
        if (s1.length() == s2.length()) return s1 < s2; // 사전 오름차순 (최대 큐이므로 반대로 리턴)
        else return s2.length() < s1.length(); // 단어 길이 내림차순(최대 큐이므로 음수 붙여서 비교)
    }
    else return c2 < c1; // 빈도수 내림차순 (최대 큐이므로 음수 붙여서 비교)
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int N; // 단어 개수. 1 ~ 10만
    int M; // 단어 길이 기준. 1 ~ 10. 길이가 M 이상인 단어로만 단어장 구성

    cin >> N >> M;

    string tmp;
    for(int i=0; i<N; i++) {
        cin >> tmp;
        if (tmp.length() < M) continue;

        word_map[tmp]++;
    }

    vector<string> arr(word_map.size());
    
    int seq = 0;
    for(auto it = word_map.begin(); it != word_map.end(); it++) {
        arr[seq++] = it->first;
    }

    sort(arr.begin(), arr.end(), word_comp);

    for(const string& s: arr) cout << s << '\n';
    cout.flush();

    return 0;
}
