#include <iostream>
#include <vector>
#include <queue>

using namespace std;

#define INF 500001

struct Node {
    int num, cost;
    Node(int num, int cost) {
        this->num = num;
        this->cost = cost;
    }
};

struct NodeComparator {
    bool operator()(Node n1, Node n2) {
        return -(n1.cost) < -(n2.cost); // 오름차순 + 최대 힙
    }
};

vector<vector<int>> graph;

int solution(int N, vector<vector<int>> road, int K) {
    // 다익스트라를 돌려 최소 거리가 K 이하인 노드들의 개수 세기
    // N: 1 ~ 50
    // road 길이: 1 ~ 2천
    
    // 1. 인접 행렬을 초기화한다.
    graph = vector<vector<int>>(N+1, vector<int>(N+1, INF));
    int n1, n2, cost;
    for(const vector<int>& edge: road) {
        n1 = edge[0];
        n2 = edge[1];
        cost = edge[2];
        // 두 마을을 연결하는 도로 중 가장 짧은 것으로 저장
        graph[n1][n2] = graph[n2][n1] = min(graph[n1][n2], cost);
    }
    
    for(int i=0; i<N+1; i++) graph[i][i] = 0;
    
    // 2. 최단 거리 배열을 초기화한다.
    vector<int> distances(N+1, INF);
    distances[1] = 0;
    
    // 3. 다익스트라를 돌린다.
    priority_queue<Node, vector<Node>, NodeComparator> pq;
    pq.push(Node(1, 0));
    
    while(!pq.empty()) {
        Node cur = pq.top();
        pq.pop();
        
        int n = cur.num;
        int c = cur.cost;
        
        for(int i=1; i<=N; i++) {
            // 해당 노드가 연결되어 있다면 (이웃노드라면)
            if(graph[n][i] != INF) {
                int new_cost = c + graph[n][i];
                
                if(new_cost < distances[i]) {
                    distances[i] = new_cost;
                    pq.push(Node(i, new_cost));
                }
                
            }
        }
        
    }
    
    int count = 0;
    for(int i=1; i<=N; i++) {
        if (distances[i] <= K) count++;
    }
    
    return count;
}