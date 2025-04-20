#include <string>
#include <vector>
#include <unordered_map>

using namespace std;

int solution(vector<string> want, vector<int> number, vector<string> discount) {
    
    // 1. discount 물건의 map을 선언하고 채운다. (첫날 ~ 열번째 날)
    unordered_map<string, int> discount_map;
    for(string d: discount) discount_map[d] = 0;
    
    string goods;
    for(int i=0; i<10; i++) {
        goods = discount[i];
        discount_map[goods]++;
    }
    
    // 2. i를 0 ~ (discount 날짜 - 10)으로 순회하면서 가능한 날짜를 카운트한다.
    int res = discount.size() - 10;
    int count = 0;
    for(int i=0; i<=res; i++) {
        // i번째 날부터, i+9번째 날의 상품이 가능한지 판별
        // 이 때 원하는 물건과 discount 물건의 map을 비교하면서 판단한다.
        for(int k = 0; k<want.size(); k++) {
            if (discount_map[want[k]] < number[k]) {
                count--;
                break;
            }
        }
        count++;
        if (i == res) break; // 마지막날은 물건 추가 x
        discount_map[discount[i]]--;
        discount_map[discount[i+10]]++;
    }
    
    int answer = count;
    return answer;
}