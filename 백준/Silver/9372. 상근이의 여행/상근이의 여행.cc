#include <iostream>
#include <vector>

using namespace std;

int find_parent(int n, vector<int>& parents) {
    if (parents[n] != n) parents[n] = find_parent(parents[n], parents);
    return parents[n];
}

void union_node(int n1, int n2, int& count, vector<int>& parents) {
    int p1 = find_parent(n1, parents);
    int p2 = find_parent(n2, parents);

    if (p1 == p2) return;

    // 더 작은 쪽이 부모
    if (p1 < p2) {
        parents[p2] = p1;
        count++;
    }
    else {
        parents[p1] = p2;
        count++;
    }
}

int calc(int n, int m) {
    // union-find를 활용한 MST (최소 신장 트리)
    vector<int> parents(n+1);
    for(int i=1; i<=n; i++) parents[i] = i;

    int a, b;
    int count = 0;
    int result = 0;
    for (int i=0; i<m; i++) {
        cin >> a >> b;
        union_node(a, b, count, parents);
        if (count == n-1) result = count;
    }

    return result;
}

int main() {

    int T;
    cin >> T;

    int N, M;
    char tmp;
    string result;
    for(int i=0; i<T; i++) {
        cin >> N >> M;
        result += to_string(calc(N, M)) + '\n';
    }

    cout << result << endl;

    return 0;
}
