#include <iostream>
#include <algorithm>

using namespace std;

string word = "";
string result = "";

void comb(string prefix, string postfix, int depth) {
    if (depth == 3) {
        postfix = string(postfix.rbegin(), postfix.rend());
        string res = prefix + postfix;
        result = min(result, res);
    }
    else {
        int length = postfix.length();
        for(int i=0; i<length-3+depth; i++) {
            string cur = postfix.substr(0, i+1); // postfix에서 떼올 만큼만 자르기
            cur = string(cur.rbegin(), cur.rend()); // 뒤집기
            // prefix + cur을 붙인 문자열을 prefix로, postfix의 남은 부분을 postfix로 지정해 다음 comb로 보내기
            comb(prefix + cur, postfix.substr(i+1), depth+1); 
        }
    }

}

int main() {

    cin >> word;
    result = string(word.length(), 'z'); // 같은 단어는 나올 수 없음.
    
    comb("", word, 1);

    cout << result << endl;

    return 0;
}
