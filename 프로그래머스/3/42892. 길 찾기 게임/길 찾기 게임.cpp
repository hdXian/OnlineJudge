#include <string>
#include <vector>

#include <queue>
#include <unordered_map>

using namespace std;

unordered_map<int, pair<int, int>> node_map;

struct comp {
    // n1, n2 노드번호에 해당하는 pair의 값을 비교
    bool operator()(int n1, int n2) {
        const pair<int, int>& p1 = node_map[n1];
        const pair<int, int>& p2 = node_map[n2];
        if (p1.second == p2.second) return p1.first > p2.first; // x좌표는 작은 순으로 정렬 (pq에 넣을꺼라 기준을 반대로)
        return p1.second < p2.second; // y좌표는 큰 순으로 (pq에 넣을꺼라 기준을 반대로)
    }
};

struct Node {
    int val;
    Node* left;
    Node* right;
    Node(int val) {
        this->val = val;
        left = nullptr;
        right = nullptr;
    }
};

class BT {
private:
    Node* root;
    
    Node* insertNode(Node* node, int val) {
        if (node == nullptr) {
            return new Node(val);
        }
        
        // x값으로 비교
        int x1 = node_map[val].first;
        int x2 = node_map[node->val].first;
        
        if (x1 >= x2) node->right = insertNode(node->right, val);
        else node->left = insertNode(node->left, val);
        
        return node;
    }
    
    void preOrder(Node* node, vector<int>& v) {
        // V - L - R
        if (node == nullptr) return;
        v.push_back(node->val);
        preOrder(node->left, v);
        preOrder(node->right, v);
    }
    
    void postOrder(Node* node, vector<int>& v) {
        if (node == nullptr) return;
        // L - R - V
        postOrder(node->left, v);
        postOrder(node->right, v);
        v.push_back(node->val);
    }
    
public:
    BT() {
        root = nullptr;
    }
    
    void insert(int val) {
        root = insertNode(root, val);
    }
    
    vector<int> getPreOrder() {
        vector<int> result;
        preOrder(root, result);
        return result;
    }
    
    vector<int> getPostOrder() {
        vector<int> result;
        postOrder(root, result);
        return result;
    }
    
};

vector<vector<int>> solution(vector<vector<int>> nodeinfo) {
    // nodeinfo: 1 ~ 1만개
    // nodeinfo[i]: 0~10만 2차원 좌표
    // 트리 깊이는 1천 이하
    
    // 노드 번호 - 좌표 매핑
    int nodes = nodeinfo.size();
    for(int i=0; i<nodes; i++) {
        node_map[i+1] = make_pair(nodeinfo[i][0], nodeinfo[i][1]);
    }
    
    // 1. 각 노드들을 y좌표가 큰 순으로 정렬한다.
    // 2. 각 노드들을 x좌표가 작은 순으로 정렬한다.
    // <저장할 요소 타입, 내부적으로 사용할 자료구조, 비교 연산을 정의해놓은 구조체>
    priority_queue<int, vector<int>, comp> pq;
    for(int i=1; i<=nodes; i++) pq.push(i);
    
    // 3. 차례대로 꺼내서 트리를 만든다.
    BT bt;
    while(!pq.empty()) {
        int n = pq.top();
        bt.insert(n);
        pq.pop();
    }
    
    vector<vector<int>> answer;
    // 4. 전위순회를 돌린다.
    answer.push_back(bt.getPreOrder());
    // 5. 후위순회를 돌린다.
    answer.push_back(bt.getPostOrder());
    
    return answer;
}