#include <string>
#include <vector>

#include <unordered_map>

using namespace std;

int solution(vector<vector<string>> clothes) {
    
    // 종류 - 개수 map
    unordered_map<string, int> counts;
    for(auto c: clothes)
        counts[c[1]]++;
    
    // 모든 종류의 의상을 함께 입는 경우의 수를 구한다.
    // 단, 해당 종류의 의상을 아예 입지 않는 경우를 고려하여 (의상 개수 + 1)을 곱해준다.
    int result = 1;
    for(auto& p: counts)
        result *= (p.second+1);
    
    int answer = result-1; // 최소 하나의 의상은 입어야하므로 아무것도 입지 않는 경우의 수 하나를 빼준다.
    return answer;
}