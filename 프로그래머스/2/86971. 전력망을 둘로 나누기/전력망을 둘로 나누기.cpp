#include <string>
#include <vector>
#include <unordered_map>
#include <iostream>

using namespace std;

vector<int> parents;
vector<int> ranks;

int N;
int result = 100;

int find_parent(int n) {
    if (parents[n] != n) parents[n] = find_parent(parents[n]);
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

void div(int pass_offset, vector<vector<int>>& wires) {
    
    parents = vector<int>(N+1);
    ranks = vector<int>(N+1);
    for(int i=1; i<=N; i++) {
        parents[i] = i;
        ranks[i] = 1;
    }
    
    int n1, n2;
    for(int i=0; i<N-1; i++) {
        if (i == pass_offset) continue;
        n1 = wires[i][0];
        n2 = wires[i][1];
        union_node(n1, n2);
    }
    
    unordered_map<int, int> mm;
    
    int p;
    for(int i=1; i<=N; i++) {
        p = find_parent(i);
        mm[p]++;
    }
    
    bool flag = true;
    int sum = 0;
    for(auto it=mm.begin(); it != mm.end(); it++) {
        if(flag) {
            sum += it->second;
            flag = false;
        }
        else sum = abs(sum - it->second);
    }
    
    // cout << "sum: " << sum << endl;
    result = min(result, sum);
}

int solution(int n, vector<vector<int>> wires) {
    // wires 길이: n-1.
    N = n;
    
    // 1. 각 전선을 제외하고 union-find로 집합을 형성한다.
    // 2. 형성된 각 집합의 원소 개수 차이 절댓값을 계산한다.
    // 3. 차이가 가장 적은 경우를 저장해 리턴한다.
    for(int i=0; i<N-1; i++) {
        div(i, wires);
    }
    
    return result;
}