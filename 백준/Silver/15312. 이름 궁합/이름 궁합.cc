#include <iostream>
#include <string>
#include <vector>

using namespace std;

int alpha[] = { 3, 2, 1, 2, 3, 3, 2, 3, 3, 2, 2, 1, 2, 2, 1, 2, 2, 2, 1, 2, 1, 1, 1, 2, 2, 1 };


string calc(string A, string B) {

    // 1. 각 알파벳을 숫자로 변환한 값을 벡터에 저장한다.
    vector<int> arr;
    for(int i=0; i<A.length(); i++) {
        arr.push_back(alpha[A[i] - 'A']);
        arr.push_back(alpha[B[i] - 'A']);
    }

    int length = arr.size();
    vector<int> prev;
    vector<int> next(arr);

    // 2. 길이가 2가 될 때까지 계산한다.
    while(length > 2) {
        prev = next;
        next.clear();

        for(int i=0; i<length-1; i++) {
            next.push_back((prev[i] + prev[i+1]) % 10);
        }

        length = next.size(); 
    }

    string result = "";
    for(int i=0; i<2; i++) result += to_string(next[i]);

    return result;
}

int main() {

    string nameA, nameB;

    cin >> nameA;
    cin >> nameB;

    string result = calc(nameA, nameB);

    cout << result << endl;

    return 0;
}
