#include <string>
#include <vector>

#include <map>
#include <iostream>

using namespace std;

vector<int> solution(string msg) {
    
    // 1. 길이가 1인 모든 단어를 포함하도록 사전을 초기화한다.
    map<string, int> indx;
    char buf[] = "A";
    for(int i=0; i<26; i++) {
        string tmp(buf);
        indx[tmp] = i+1;
        buf[0]++;
    }
    
    // 2. 사전에서 해당 입력과 일치하는 가장 긴 문자열을 찾는다.
    vector<int> answer;
    string res(msg.begin(), msg.end());
    int seq = 27;
    while(res.length() > 0) {
        
        // 남은 문자열 전체가 사전에 있으면 답에 추가하고 break
        if (indx.find(res) != indx.end()) {
            answer.push_back(indx[res]);
            break;
        }
        
        // 사전에 없는 문자열이 나타날 때까지 문자열을 이어붙인다 (사전에 존재하는 가장 긴 문자열을 찾는다.)
        int i = 1;
        while(i < res.length() && indx.find(res.substr(0, i)) != indx.end()) i++;
        
        // 사전에 존재하는 가장 긴 문자열(0 ~ seq-1)의 인덱스를 답에 추가하고, 없는 문자열을 사전에 추가한다.
        answer.push_back(indx[res.substr(0, i-1)]);
        
        indx[res.substr(0, i)] = seq++;
        
        // 대상 문자열을 자른다.
        res = res.substr(i-1);
    }
    
    return answer;
}

