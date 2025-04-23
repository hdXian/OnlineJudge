#include <string>
#include <vector>

#include <unordered_map>
#include <algorithm>

using namespace std;

// second 기준 내림차순 정렬
bool compare(const pair<string, long>& p1, const pair<string, long>& p2) {
    return p1.second > p2.second;
}

bool song_compare(const pair<int, int>& p1, const pair<int, int>& p2) {
    return p1.second > p2.second;
}

vector<int> solution(vector<string> genres, vector<int> plays) {
    // 1. 장르-플레이 횟수로 map 선언
    // map을 value 기준으로 정렬하려면 vector로 뽑아낸 다음 vector를 정렬해야 함.
    unordered_map<string, long> total_plays;
    int length = genres.size();
    for(int i=0; i<length; i++) {
        total_plays[genres[i]] += plays[i];
    }
    
    // 2. 플레이 횟수가 많은 순으로 장르 정렬
    vector<pair<string, long>> tp_v(total_plays.begin(), total_plays.end());
    sort(tp_v.begin(), tp_v.end(), compare);
    
    // 3. 장르별로 곡 2개씩 수록
    // 번호-플레이 횟수를 저장할 vector
    vector<pair<int, int>> songs;
    for(int i=0; i<length; i++) songs.push_back(make_pair(i, plays[i]));
    
    // 재생횟수 순으로 정렬
    sort(songs.begin(), songs.end(), song_compare);
    
    vector<int> answer;
    // 재생횟수 많은 순으로 장르 순회
    for(const pair<string, long>& p: tp_v) {
        string g = p.first;
        int seq = 0;
        
        // 해당 장르 곡 2개씩 수록
        for(const pair<int, int>& s: songs) {
            int idx = s.first;
            if (genres[idx] == g) {
                answer.push_back(idx);
                seq++;
            }
            if (seq >=2) break;
        }
        
    }
    
    return answer;
}


