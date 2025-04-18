#include <string>
#include <vector>
#include <queue>

using namespace std;

string solution(vector<string> cards1, vector<string> cards2, vector<string> goal) {
    
    queue<string> c1;
    for(const string& s: cards1) c1.push(s);
    queue<string> c2;
    for(const string& s: cards2) c2.push(s);
    
    string answer = "Yes";
    
    for(const string& word: goal) {
        if (c1.front() == word) c1.pop();
        else if (c2.front() == word) c2.pop();
        else {
            answer = "No";
            break;
        }
    }
    
    return answer;
}