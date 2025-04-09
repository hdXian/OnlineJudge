#include <string>
#include <vector>

using namespace std;

vector<int> solution(vector<int> answers) {
    // 1번: 1, 2, 3, 4, 5, ...
    // 2번: 2, 1, 2, 3, 2, 4, 2, 5, ...
    // 3번: 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, ...
    
    vector<int> pattern1 = {1, 2, 3, 4, 5}; // 5
    vector<int> pattern2 = {2, 1, 2, 3, 2, 4, 2, 5}; // 8
    vector<int> pattern3 = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}; // 10
    
    int length = answers.size();
    vector<int> students(3, 0);
    
    int val;
    for(int i=0; i<length; i++) {
        val = answers[i];
        
        if (val == pattern1[i%5]) students[0]++;
        if (val == pattern2[i%8]) students[1]++;
        if (val == pattern3[i%10]) students[2]++;
    }
    
    int max_val = -1;
    for(int i=0; i<3; i++)
        if (max_val < students[i])
            max_val = students[i];
    
    vector<int> answer;
    for(int i=0; i<3; i++) {
        if (students[i] == max_val)
            answer.push_back(i+1);
    }
    
    return answer;
}