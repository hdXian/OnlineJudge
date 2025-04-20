#include <string>
#include <vector>
#include <unordered_set>

using namespace std;

vector<int> solution(int n, vector<string> words) {
    // 사람 n명
    unordered_set<string> hash_set;
    hash_set.insert(words[0]);
    
    vector<int> answer(2, 0);
    int length = words.size();
    for(int i=1; i<length; i++) {
        if (words[i].front() != words[i-1].back() || hash_set.find(words[i]) != hash_set.end() ) {
            answer[0] = (i % n)+1; // 0 1 2 3 4 -> 0 1 0 1 0 -> 1 2 1 2 1
            answer[1] = (i / n) + 1; // 0 1 2 3 4 -> 0 0 1 1 2 -> 1 1 2 2 3
            break;
        }
        else {
            hash_set.insert(words[i]);
        }
    }

    return answer;
}