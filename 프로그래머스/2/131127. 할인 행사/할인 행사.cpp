#include <string>
#include <vector>
#include <unordered_map>

using namespace std;

int solution(vector<string> want, vector<int> number, vector<string> discount) {
    
    // 1. discount 물건의 map을 선언하고 채운다. (첫날 ~ 열번째 날)
    unordered_map<string, int> discount_map;
    for(string d: discount) discount_map[d] = 0;
    
    for(int i=0; i<9; i++) discount_map[discount[i]]++; // 0~8, 9일간 물건 카운트
    
    // 2. i를 0 ~ (discount 날짜 - 10)으로 순회하면서 가능한 날짜를 카운트한다.
    // 9 ~ discount 길이만큼 순회도 가능.
    int count = 0;
    for(int i=9; i<discount.size(); i++) {
        discount_map[discount[i]]++;
        
        for(int k=0; k<want.size(); k++) {
            if (discount_map[want[k]] < number[k]) {
                count--; 
                break;
            }
        }
        
        count++;
        discount_map[discount[i-9]]--;
    }
    
    int answer = count;
    return answer;
}