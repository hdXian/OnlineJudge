#include <string>
#include <vector>

#include <unordered_map>
#include <sstream>
#include <iostream>

using namespace std;

void process(unordered_map<string, string>& nicknames, vector<pair<string, bool>>& history, string msg) {
    // msg: Enter uid1234 Muzi
    istringstream iss(msg);
    string str;
    
    vector<string> args;
    while (getline(iss, str, ' ')) {
        args.push_back(str);
    }
    // args = ["Enter", "uid1234", "Muzi"]
    // "Enter", "Leave", "Change"
    // 'E', 'L', 'C'
    
    string uid, nickname;
    // args[0] = "Enter" or "Laeve" or "Change"
    char head = args[0][0]; // head = 'E', 'L', 'C'
    if (head == 'E') {
        uid = args[1];
        nickname = args[2];
        nicknames[uid] = nickname; // map에 저장
        history.push_back(make_pair(uid, true)); // Enter uid1234 Muzi -> (uid1234, true)
    }
    else if (head == 'L') {
        uid = args[1];
        history.push_back(make_pair(uid, false)); // Leave uid1234 -> (uid1234, false)
    }
    else { // 'C'
        uid = args[1];
        nickname = args[2];
        nicknames[uid] = nickname;
    }
    
}

vector<string> solution(vector<string> record) {
    // record 길이: 1 ~ 10만
    // Enter uid1234 Muzi
    // Leave uid1234
    // Change uid4567 Ryan
    
    // uid - nickname 매칭할 map
    unordered_map<string, string> nicknames;
    
    // 출력 메시지를 기록할 pair 리스트 (uid-in/out/change , 0/1/2)
    vector<pair<string, bool>> history;
    
    // 1. record를 순회하면서 uid-닉네임을 매칭시킨다.
    for(string s: record) {
        process(nicknames, history, s);
    }
    
    // pair<string, vool> p = (uid1234, true) ==> p.first = uid1234, p.second = true
    // history = [ (uid1234, true), (uid1234, false), (uid4567, true) ]
    vector<string> answer;
    for(const pair<string, bool>& p: history) {
        string line = nicknames[p.first] + "님이 "; // line = "Prodo님이 "
        line += (p.second) ? "들어왔습니다." : "나갔습니다."; // line = "Prodo님이 들어왔습니다."
        answer.push_back(line);
    }
        
    return answer;
}