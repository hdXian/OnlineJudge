#include <string>
#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;

bool solution(vector<string> phone_book) {
    // 접두어인 경우가 있으면 false
    // 없으면 true
    // 같은 전화번호 중복 없음
    
    // 1. 각 전화번호를 오름차순 정렬한다. (문자 사전 순으로 정렬됨)
    sort(phone_book.begin(), phone_book.end());
    // for(string p: phone_book) cout << p << endl;
    
    // 2. 각 원소가 자신의 바로 뒤 원소의 접두어가 될 수 있으면 false
    int length = phone_book.size();
    string cur, pre;
    for(int i=1; i<length; i++) {
        cur = phone_book[i];
        pre = phone_book[i-1];
        if (pre == cur.substr(0, pre.length())) return false;
    }
    
    return true;
}