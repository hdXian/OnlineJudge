#include <string>
#include <vector>
#include <unordered_set>

using namespace std;

vector<int> solution(int n, vector<string> words) {
    // 사람 n명
    unordered_set<string> hash_set;
    int length = words.size();
    
    int end_turn;
    int end_p;
    string word;
    char pre = words[0][0];
    vector<int> answer(2, 0);
    for(int i=0; i<length; i++) {
        word = words[i];
        if (word[0] != pre || hash_set.find(word) != hash_set.end() ) {
            end_p = (i % n)+1; // 0 1 2 3 4 -> 0 1 0 1 0 -> 1 2 1 2 1
            end_turn = (i / n) + 1; // 0 1 2 3 4 -> 0 0 1 1 2 -> 1 1 2 2 3
            answer[0] = end_p;
            answer[1] = end_turn;
            break;
        }
        else {
            hash_set.insert(word);
            pre = word[word.length()-1];
        }
    }

    return answer;
}