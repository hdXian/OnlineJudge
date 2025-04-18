#include <string>
#include <vector>
#include <queue>

using namespace std;

vector<int> solution(vector<int> progresses, vector<int> speeds) {
    
    // 각 작업을 큐에 집어넣는다.
    int length = progresses.size();
    queue<int> q;
    for(int i=0; i<length; i++) q.push(i);
    
    vector<int> answer;
    // 진행도를 차례대로 더한다.
    int count;
    while (!q.empty()) {
        count = 0;
        
        for(int i=0; i<length; i++)
            progresses[i] += (progresses[i] >= 100) ? 0 : speeds[i];
        
        while (!q.empty() && progresses[q.front()] >= 100) {
            q.pop();
            count++;
        }
        
        if(count != 0)
            answer.push_back(count);
    }
    // 큐의 헤드에 위치한 작업이 완료된 경우, poll하는 작업을 반복한다.
    
    return answer;
}