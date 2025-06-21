#include <string>
#include <vector>

using namespace std;

vector<int> parents;
vector<int> ranks;

int find_parent(int n) {
    if(parents[n] != n) parents[n] = find_parent(parents[n]);
    return parents[n];
}

void union_node(int n1, int n2) {
    int p1 = find_parent(n1);
    int p2 = find_parent(n2);
    
    if (p1 == p2) return;
    
    int r1 = ranks[p1];
    int r2 = ranks[p2];
    if (r1 == r2) {
        parents[p2] = p1;
        ranks[p1]++;
    }
    else if (r1 > r2) parents[p2] = p1;
    else parents[p1] = p2;
    
}

int solution(int n, vector<vector<int>> computers) {
    
    // 1. parents, ranks 배열을 선언한다.
    parents = vector<int>(n);
    ranks = vector<int>(n);
    for(int i=0; i<n; i++) {
        parents[i] = i;
        ranks[i] = 1;
    }
    
    // 2. 서로 연결되어있는 컴퓨터끼리 union한다.
    for(int i=0; i<n; i++) {
        for(int j=i; j<n; j++) {
            if(computers[i][j] == 1) union_node(i, j);
        }
    }
    
    // 3. root 노드의 개수를 센다.
    int roots = 0;
    for(int i=0; i<n; i++) {
        if (i == find_parent(i)) roots++;
    }
    
    return roots;
}
