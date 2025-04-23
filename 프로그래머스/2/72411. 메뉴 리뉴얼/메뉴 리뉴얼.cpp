#include <string>
#include <vector>

#include <unordered_map>
#include <algorithm>

using namespace std;

// second 기준 내림차순
bool compare(const pair<string, int>& p1, const pair<string, int>& p2) {
    return p1.second > p2.second;
}

void comb(string prefix, string postfix, int length, int depth, unordered_map<string, int>& count) {
    // 현재 자기 depth를 바탕으로 for문을 어느 범위로 순회할지 결정해야 함.
    if (depth == length) {
        for(char ch: postfix) {
            count[prefix + ch]++;
        }
    }
    else {
        int l = postfix.length();
        for(int i=0; i<postfix.length(); i++) {
            comb(prefix + postfix[i], postfix.substr(i+1,l-i-1), length, depth+1, count);
        }
    }
    
}

vector<string> solution(vector<string> orders, vector<int> course) {
    // 구성 메뉴 개수 - 조합별 카운트
    unordered_map<int, unordered_map<string, int>> counts;
    
    // 1. 각 손님의 주문을 사전 순 정렬한다.
    for(string& order: orders) {
        sort(order.begin(), order.end());
    }
    
    // 2. 각 손님의 주문 조합에서 나올 수 있는 모든 조합을 생성해 카운트한다.
    for(string order: orders) {
        for(int c: course) {
            if (c <= order.length()) comb("", order, c, 1, counts[c]);
        }
    }
    
    // 3. course에 나온 길이별 조합 중에서 가장 카운트가 많은 조합을 선택한다.
    vector<string> answer;
    for(int c: course) {
        if(counts[c].empty()) continue;
        
        // 길이가 c인 메뉴 조합들(counts[c] 원소들)을 vector에 저장 후 sort
        vector<pair<string, int>> combs(counts[c].begin(), counts[c].end());
        sort(combs.begin(), combs.end(), compare);
        int _maxL = combs[0].second;
        if (_maxL < 2) continue; // 가장 많이 선택한 메뉴가 1인 경우 추가하지 않음
        
        // 해당 길이의 메뉴조합 중 가장 많이 선택한 메뉴 조합을 answer에 push
        for(auto &p: combs) {
            if (p.second == _maxL) answer.push_back(p.first);
            else break;
        }
        
    }
    
    sort(answer.begin(), answer.end()); // 마지막으로 answer에 추가된 메뉴들을 사전 순 정렬
    return answer;
}