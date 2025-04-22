#include <string>
#include <vector>

#include <iostream>
#include <stack>
#include <cmath>

using namespace std;

string tobinary(long long number) {
    stack<long long> st;
    while(number != 0) {
        st.push(number%2);
        number /= 2;
    }
    
    string bin;
    while(!st.empty()) {
        bin += to_string(st.top());
        st.pop();
    }
    return bin;
}

bool binary_search(const string& bin, int parent, int start, int end) {
    if (start > end) return true;
    
    if (start == end) {
        if (parent == 0 && bin[start] == '1') return false;
        return true;
    }
    
    int mid = (start + end) / 2;
    int cur = bin[mid] == '1' ? 1 : 0;
    
    if (parent == 0 && cur == 1) return false;
    
    bool l_sub = binary_search(bin, cur, start, mid-1);
    bool r_sub = binary_search(bin, cur, mid+1, end);
    return l_sub && r_sub;
}

// 2. 트리의 높이와 노드 개수를 구하고, 트리를 그린다.
bool calc(string bin) {
    int blength = bin.length();
    double h = floor(log(blength) / log(2.0)) + 1;
    int nodes = pow(2, h) - 1;
    
    // 완전 이진트리 만들기
    string zeros = "";
    for(int i=blength; i<nodes; i++) zeros += "0";
    
    string full_bin = zeros + bin;
    if (full_bin[full_bin.length()/2] == '0') return false;
    return binary_search(full_bin, 1, 0, nodes);
}

vector<int> solution(vector<long long> numbers) {
    
    // 1. 각 숫자를 이진수로 표현한다.
    // 2. 트리의 높이와 노드 개수를 구하고, 트리를 그린다.
    // 3. 0인 노드의 자식 노드는 1이어선 안된다.
    vector<int> answer;
    string str;
    for(long long n: numbers) {
        str = tobinary(n);
        bool result = calc(str);
        answer.push_back(result);
    }
    
    
    return answer;
}