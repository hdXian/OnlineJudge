#include <iostream>
#include <string>
#include <vector>
#include <unordered_map>

using namespace std;

bool compare(pair<int, int> p1, pair<int, int> p2) {
    if (p1.second == p2.second)
        return p1.first < p2.first;
    
    return p1.second > p2.second;
}

vector<int> solution(vector<int> answers) {
    // 1번: 1, 2, 3, 4, 5, ...
    // 2번: 2, 1, 2, 3, 2, 4, 2, 5, ...
    // 3번: 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, ...
    
    vector<int> pattern1 = {1, 2, 3, 4, 5}; // 6
    vector<int> pattern2 = {2, 1, 2, 3, 2, 4, 2, 5}; // 8
    vector<int> pattern3 = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}; // 10
    
    int length = answers.size();
    unordered_map<int, int> mm;
    mm[1] = 0;
    mm[2] = 0;
    mm[3] = 0;
    
    int val;
    for(int i=0; i<length; i++) {
        val = answers[i];
        
        if (val == pattern1[i%5]) mm[1]++;
        if (val == pattern2[i%8]) mm[2]++;
        if (val == pattern3[i%10]) mm[3]++;
    }
    
    cout << mm[1] << endl;
    cout << mm[2] << endl;
    cout << mm[3] << endl;
    
    int max_val = -1;
    for(int i=1; i<=3; i++)
        if (max_val < mm[i])
            max_val = mm[i];
    
    vector<int> answer;
    for(int i=1; i<=3; i++) {
        if (mm[i] == max_val)
            answer.push_back(i);
    }
    
    return answer;
}