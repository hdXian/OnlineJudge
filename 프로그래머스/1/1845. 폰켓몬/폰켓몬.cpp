#include <vector>
#include <iostream>
#include <unordered_set>

using namespace std;

int solution(vector<int> nums) {
    // 1. 각 종류 번호를 저장할 set을 선언한다.
    // 2. 각 종류 번호를 집합에 집어넣는다.
    // 3. 집합의 크기와 nums 크기를 비교하여 답을 리턴한다.
    int length = nums.size();
    
    unordered_set<int> ss;
    for(int n: nums) ss.insert(n);
    
    int siz = ss.size();
    
    // 종류가 length/2보다 적으면 -> 전체 종류 개수 리턴
    // 종류 개수가 length/2보다 크면 -> length/2 리턴
    if (ss.size() > (length/2)) return length/2;
    else return ss.size();
}