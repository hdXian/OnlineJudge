#include <iostream>
#include <set>
#include <string>

using namespace std;

struct shortOrder {
    bool operator() (const string& left, const string& right) const {
        if (left.size() == right.size())
            return left < right;
        else
            return left.size() < right.size();
    }
};

int main() {

    // 1 <= n <= 20000
    int n;
    cin >> n;
    //cout << "n: " << n << endl;

    set<string, shortOrder> s;

    string str;
    for (int i = 0; i < n; i++) {
        cin >> str;
        //cout << "i: "<< i << ", "<<str << endl;
        s.insert(str);
    }

    //putchar('\n');
    for (auto it = s.begin(); it != s.end(); it++) {
        cout << *it << endl;
    }

}

