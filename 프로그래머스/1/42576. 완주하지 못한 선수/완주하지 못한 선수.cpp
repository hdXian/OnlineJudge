#include <string>
#include <vector>
#include <unordered_map>

using namespace std;

string solution(vector<string> participant, vector<string> completion) {
    
    // 이름, 통과한 사람 수
    unordered_map<string, int> hash_map;
    
    // 통과한 사람이 map에 있으면 해당 키에 대응하는 값을 1 증가
    // 없으면 이름을 키로, 1을 값으로 하는 새로운 엔트리 추가
    for(string c: completion) {
        auto it = hash_map.find(c);
        if (it == hash_map.end()) hash_map[c] = 1;
        else hash_map[c]++;
    }
    
    // map에 아예 없으면 바로 해당 이름 리턴
    // map에 있는데, 해당 값이 0이면 해당 이름 리턴
    // map에 있는데, 0이 아니면 값 감소 (동명이인 중 일부만 통과함)
    string answer = "";
    for(string p: participant) {
        auto it = hash_map.find(p);
        if (it == hash_map.end()) {
            answer = p;
            break;
        }
        else if (hash_map[p] == 0) {
            answer = p;
            break;
        }
        else {
            hash_map[p]--;
        }
    }
    
    return answer;
}