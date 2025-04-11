#include <string>
#include <vector>
#include <algorithm>

using namespace std;

class Stage {
    
public:
    int number;
    double fail;
    
    Stage() {}
    Stage(int number, double fail) {
        this->number = number;
        this->fail = fail;
    }
    void setNumber(int number) { this->number = number; }
    void setFail(double fail) { this->fail = fail; }
};

bool compare(const Stage& s1, const Stage& s2) {
    if (s1.fail == s2.fail)
        return s1.number < s2.number;
    
    return s1.fail > s2.fail;
}

vector<int> solution(int N, vector<int> stages) {
    // 각 스테이지에 도달한 사람의 수를 센다.
    // 각 스테이지를 클리어하지 못한 사람의 수를 센다. (아직 도달하지 못한 사람은 세지 않아야 함.)
    // 실패율을 계산하고, 내림차순 정렬한다.
    
    vector<int> reached;
    reached.assign(N+1, 0); // 인덱스 1부터 쓰기
    
    vector<int> fails;
    fails.assign(N+1, 0);
    
    for(int s: stages) {
        for(int i=1; i<s; i++) {
            reached[i]++;
        }
        if (s != N+1) {
            reached[s]++;
            fails[s]++;
        }
    }
    
    vector<Stage> ss;
    double fail;
    for(int i=1; i<=N; i++) {
        if (reached[i] == 0)
            fail = 0;
        else
            fail = (double)fails[i] / reached[i];
        ss.push_back(Stage(i, fail));
    }
    
    sort(ss.begin(), ss.end(), compare);
    
    vector<int> answer;
    for(Stage s: ss) {
        answer.push_back(s.number);
    }
    
    return answer;
}