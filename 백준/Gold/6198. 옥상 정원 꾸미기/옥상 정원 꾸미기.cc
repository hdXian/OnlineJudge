#include <iostream>
#include <string>
#include <vector>
using namespace std;

void init(int& N, vector<int>& hs) {
    cin >> N;
    
    int tmp;
    for(int i=0; i<N; i++) {
        cin >> tmp;
        hs.push_back(tmp);
    }

}

long calc(vector<int>& hs) {

    long result = 0;

    vector<int> stack;

    for(int h: hs) {
        
        auto it = stack.rbegin();
        while (it != stack.rend() && (*it <= h)) {
            stack.pop_back();
            it++;
        }

        result += stack.size();

        stack.push_back(h);
    }

    return result;
}

int main() {

    int N;
    vector<int> hs;
    init(N, hs);

    long result = calc(hs);
    cout << result << endl;

    return 0;
}
