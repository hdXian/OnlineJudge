#include <string>
#include <vector>

#include <unordered_map>
#include <unordered_set>
#include <sstream>

using namespace std;

void process(unordered_map<string, unordered_set<string>>& reported, string report_log) {
    istringstream iss(report_log);
    string arg1, arg2;
    iss >> arg1 >> arg2;
    
    reported[arg2].insert(arg1); // arg1이 arg2를 신고함
}

vector<int> solution(vector<string> id_list, vector<string> report, int k) {
    
    // 각 유저를 신고한 유저들을 저장하는 map
    unordered_map<string, unordered_set<string>> reported;
    for(string r: report)
        process(reported, r);
    
    // 각 유저가 받은 정지 알림 메일 수를 저장하는 map
    unordered_map<string, int> mails;
    
    // 각 id에 대해
    for(string id: id_list) {
        // 해당 id를 신고한 유저들을 저장한 set 크기가 k 이상이면
        if (reported[id].size() >= k) {
            // 신고한 유저들이 받은 메일 개수를 1 증가시킨다.
            for(string report_id: reported[id]) {
                mails[report_id]++;
            }
        }
    }
    
    vector<int> answer;
    for(string id: id_list)
        answer.push_back(mails[id]);
    return answer;
}