#include <string>
#include <vector>

#include <unordered_map>
#include <sstream>
#include <iostream>

using namespace std;

void process(unordered_map<string, string>& nicknames, vector<pair<string, bool>>& history, string msg) {
    istringstream iss(msg);
    string str;
    
    vector<string> args;
    while (getline(iss, str, ' ')) {
        args.push_back(str);
    }
    
    string uid, nickname;
    char head = args[0][0];
    if (head == 'E') {
        uid = args[1];
        nickname = args[2];
        nicknames[uid] = nickname;
        history.push_back(make_pair(uid, true));
    }
    else if (head == 'L') {
        uid = args[1];
        history.push_back(make_pair(uid, false));
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
    
    for(string s: record) {
        process(nicknames, history, s);
    }
    
    vector<string> answer;
    for(const pair<string, bool>& p: history) {
        string line = nicknames[p.first] + "님이 ";
        line += (p.second) ? "들어왔습니다." : "나갔습니다.";
        answer.push_back(line);
    }
        
    return answer;
}