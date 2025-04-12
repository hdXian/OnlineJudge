#include <string>
#include <vector>
#include <algorithm>

using namespace std;

bool compare(pair<double, int> p1, pair<double, int> p2) {
    if (p1.first == p2.first)
        return p1.second < p2.second;
    return p1.first > p2.first;
}

vector<int> solution(int N, vector<int> stages) {
    
    // 각 stage별로 도달한 사람과 실패한 사람을 구한다.
    vector<int> reached(N+1);
    vector<int> fails(N+1);
    
    for(int s: stages) {
        for(int i=0; i<s; i++)
            reached[i]++;
        fails[s-1]++;
    }
    
    // fails가 N번 인덱스에 해당하는건 끝까지 다 깬 사람. 실패율 계산할 때 고려 안할꺼임.
    
    vector<pair<double, int>> rates(N);
    for(int i=0; i<N; i++) {
        if (reached[i] == 0)
            rates[i].first = 0;
        else
            rates[i].first = (double)fails[i] / reached[i];
        rates[i].second = i+1;
    }
    
    sort(rates.begin(), rates.end(), compare);
    
    vector<int> answer;
    for(pair<double, int> r: rates) {
        answer.push_back(r.second);
    }
    return answer;
}