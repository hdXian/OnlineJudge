#include <string>
#include <vector>
#include <queue>

using namespace std;

int find_parent(int n, vector<int>& parents) {
    if (parents[n] != n) parents[n] = find_parent(parents[n], parents); // 경로 압축
    return parents[n];
}

bool union_node(int n1, int n2, vector<int>& ranks, vector<int>& parents) {
    int p1 = find_parent(n1, parents);
    int p2 = find_parent(n2, parents);
    
    if (p1 == p2) return false;
    
    int r1 = ranks[p1];
    int r2 = ranks[p2];
    
    // 트리 깊이를 최대한 덜 늘리기 위해 ranks가 큰 쪽으로 union
    if (r1 == r2) {
        parents[p2] = p1;
        ranks[p1]++;
    }
    else if (r1 > r2) {
        parents[p2] = p1;
    }
    else
        parents[p1] = p2;
    
    return true;
}

struct Edge {
    int p1, p2;
    int cost;
    Edge(int p1, int p2, int cost) {
        this->p1 = p1;
        this->p2 = p2;
        this->cost = cost;
    }
};

struct EdgeComparator {
    bool operator() (const Edge& e1, const Edge& e2) {
        return -(e1.cost) < -(e2.cost); // cost 기준 오름차순 (최대 큐이므로 음수 처리하여 비교)
    }
};

int solution(int n, vector<vector<int>> costs) {
    // costs[i]: 섬1 섬2 비용
    
    // 1. 각 섬을 연결하는 도로 정보를 저장한다.
    // 2. 각 도로를 비용 순으로 오름차순 정렬하고 하나씩 뽑는다.
    // 3. 뽑은 도로가 연결한 양쪽 섬이 같은 집합에 포함되지 않은 경우, 해당 도로를 선택한다.
    // 4. 같은 집합에 포함되어있는 경우, 어떻게든 연결되어 있다는 의미이므로 해당 도로를 선택하지 않는다.
    // 5. 선택한 도로 수가 n-1개가 되거나 큐가 빌 때까지 반복한다.
    // 6. 문제 조건에는 없지만, 큐가 빌 때까지 도로를 뽑았는데, 선택된 도로 수가 n-1보다 적은 경우,
    // -> 모든 섬을 연결할 방법이 없는 케이스로 판별한다.
    
    // union-find에 사용할 부모, 랭크 배열 선언
    vector<int> parents(n);
    for(int i=0; i<n; i++) parents[i] = i;
    
    vector<int> ranks(n, 0);
    
    
    // 비용 순으로 오름차순 정렬하는 pq 선언
    // priority_queue<타입, 담을 자료구조, 비교 구조체 이름> pq;
    priority_queue<Edge, vector<Edge>, EdgeComparator> pq;
    int siz = costs.size();
    int p1, p2, cost;
    for(int i=0; i<siz; i++) {
        p1 = costs[i][0];
        p2 = costs[i][1];
        cost = costs[i][2];
        // 출발지, 목적지, 비용 정보를 담은 Edge 객체를 pq에 추가
        pq.push(Edge(p1, p2, cost));
    }
    
    int edge_count = 0;
    int total_cost = 0;
    
    // 큐가 비거나 선택된 도로가 n-1개가 될 때까지 반복
    while(!pq.empty() && edge_count<n-1) {
        Edge cur = pq.top();
        // 부모가 달라서 집합을 합치는 경우 (return true) 해당 도로를 선택했다는 의미
        // 도로 개수를 카운트하고, 전체 비용에도 더한다.
        if (union_node(cur.p1, cur.p2, ranks, parents)) {
            edge_count++;
            total_cost += cur.cost;
        }
        pq.pop();
    }
    
    return total_cost;
}