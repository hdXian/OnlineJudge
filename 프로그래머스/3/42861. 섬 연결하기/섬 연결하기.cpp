#include <string>
#include <vector>
#include <queue>

using namespace std;

vector<int> parents;
vector<int> ranks;

int find_parent(int n) {
    if (parents[n] != n) parents[n] = find_parent(parents[n]);
    return parents[n];
}

bool union_node(int n1, int n2) {
    int p1 = find_parent(n1);
    int p2 = find_parent(n2);
    if (p1 == p2) return false;
    
    int r1 = ranks[p1];
    int r2 = ranks[p2];
    if (r1 == r2) {
        parents[p2] = p1;
        ranks[p1]++;
    }
    else if (r1 > r2) parents[p2] = p1;
    else parents[p1] = p2;
    
    return true;
}

struct Edge {
    int p1, p2, cost;
    Edge(int p1, int p2, int cost) {
        this->p1 = p1;
        this->p2 = p2;
        this->cost = cost;
    }
};

struct EdgeComparator {
    bool operator() (Edge e1, Edge e2) {
        return -e1.cost < -e2.cost; // c++ pq는 기본적으로 최대 큐이므로 정렬 기준을 반대로 정의
    }
};

int solution(int n, vector<vector<int>> costs) {
    // n: 섬 개수. 1 ~ 100
    // costs[i][0]: 섬1, costs[i][1]: 섬2, costs[i][2]: 비용
    
    // MST 만들기
    // 1. 부모 배열, 랭크 배열을 초기화한다.
    for(int i=0; i<n; i++) {
        parents.push_back(i);
        ranks.push_back(0);
    }
    
    // 2. 간선들을 비용 순으로 정렬한다.
    priority_queue<Edge, vector<Edge>, EdgeComparator> pq;
    for(int i=0; i<costs.size(); i++) {
        // 점1, 점2, 비용
        pq.push(Edge(costs[i][0], costs[i][1], costs[i][2]));
    }
    
    // 3. 큐가 비거나 간선을 n-1개 선택할 때까지, 각 점이 서로 다른 집합에 속해있는 간선을 선택한다.
    int edge_count = 0;
    int total_cost = 0;
    while(!pq.empty() && edge_count < (n-1)) {
        Edge cur = pq.top();
        pq.pop();
        if (union_node(cur.p1, cur.p2)) {
            total_cost += cur.cost;
            edge_count++;
        }
    }
    
    return total_cost;
}