#include <string>
#include <vector>
#include <set>

using namespace std;

vector<int> solution(vector<int> numbers) {
    
    int length = numbers.size();
    // 인덱스를 N*N으로 돌면서 각 인덱스의 요소를 더한다.
    // 더한 값을 set에 담는다.
    // 이걸 vector로 만들어서 정렬해 리턴한다.
    
    set<int> s;
    for(int i=0; i<length; i++) {
        for(int j=i+1; j<length; j++) {
            s.insert(numbers[i] + numbers[j]);
        }
    }
    
    vector<int> answer;
    for(int i: s)
        answer.push_back(i);
    
    return answer;
}