#include <iostream>
#include <queue>
#include <algorithm>

using namespace std;

static int N, L, P;

struct Station {
    int distance;
    int gas;
    Station(int distance, int gas) {
        this->distance = distance;
        this->gas = gas;
    }
};

// c++ pq는 내림차순이라서 기준을 반대로 넣고 싶으면 비교 함수를 오름차순으로 전달해야 함.
bool station_compare(Station &s1, Station &s2) {
    // int cost1 = s1.gas - s1.distance;
    // int cost2 = s2.gas - s2.distance;
    return s1.gas < s2.gas;
}

// c++ pq는 기본적으로 내림차순이기 때문에, 원하는 기준의 반대로 넣어서 전달해야 함.
bool distance_compare(Station &s1, Station &s2) {
    return s2.distance < s1.distance;
}

static priority_queue<Station, vector<Station>, decltype(&distance_compare)> stations(distance_compare);

void init() {
    cin >> N; // 주유소 개수. 1 ~ 1만
    
    int a, b;
    for(int i=0; i<N; i++) {
        cin >> a >> b; // 시작 위치에서 주유소까지의 거리 (1 ~ 100만), 채울 수 있는 연료의 양 (1 ~ 100)
        stations.push(Station(a, b));
    }

    cin >> L; // 목적지까지 거리
    cin >> P; // 처음에 들어있는 연료의 양
}

int calc() {
    int cur = 0; // 현재 위치
    int remain_gas = P; // 현재 남은 연료
    int remain_distance = L; // 목적지까지 남은 거리

    // 방문 가능한 주유소들을 저장할 덱.
    priority_queue<Station, vector<Station>, decltype(&station_compare)> pq(station_compare);

    int count = 0; // 방문한 주유소 카운트
    
    int max_reachable;
    while (true) {
        // cout << "cur: " << cur << endl;

        if (remain_distance <= remain_gas) break; // 남은 거리가 남은 연료보다 적다면 종료. 주유소를 더 이상 들리지 않아도 됨.

        max_reachable = cur + remain_gas; // 갈 수 있는 최대 거리: 현재 위치 + 남은 가스

        // 1. 현재 위치에서 가진 연료료 도달할 수 있는 주유소들을 탐색
        while((!stations.empty()) && (stations.top().distance <= max_reachable)) {
            Station f = stations.top();
            pq.push(f);
            stations.pop();
        }
        
        // 방문할 수 있는 주유소가 없을 경우 -1 리턴. 목적지에 도착할 수 없음.
        if (pq.empty()) {
            count = -1;
            break;
        }

        // 정의한 기준에 따라 Stations들을 정렬.
        Station next = pq.top(); // 가장 앞의 주유소가 다음에 방문할 주유소
        pq.pop();

        remain_gas = remain_gas - (next.distance - cur) + next.gas;
        cur = next.distance;
        remain_distance = L - cur;

        count++;
    }

    return count;
}

int main() {
    init();
    int result = calc();
    cout << result << endl;
}

