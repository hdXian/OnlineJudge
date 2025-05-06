#include <string>
#include <vector>

#include <unordered_map>
#include <cmath>

#include <iostream>

using namespace std;

unordered_map<string, int> incomes; // 이름-수익금을 매칭하는 map
unordered_map<string, string> parents; // 자신의 부모 이름을 저장하는 map

void calcIncome(string seller_name, int price) {
    double tenp;
    int income;
    // 부모가 "-"가 아닐동안
    while(seller_name != "-") {
        if (price == 0) break;
        
        // 판매량의 10%를 부모에게 상납
        // 10%를 계산하고, 원 단위로 절삭한 다음 가질 양, 상납할 양을 게산
        tenp = price * 0.1;

        // 10%가 1원 미만일 경우 상납하지 않음
        if (tenp < 1) {
            incomes[seller_name] += price;
            price = 0;
        }
        
        else {
            tenp = floor(tenp);
            income = price - tenp;
            incomes[seller_name] += income;
            price = tenp;
        }
        
        seller_name = parents[seller_name]; // 부모로 넘어감
    }
    
}


vector<int> solution(vector<string> enroll, vector<string> referral, vector<string> seller, vector<int> amount) {
    // enroll: 1 ~ 1만 길이, 조직원들
    // referral: enroll과 같은 길이. 각 조직원을 추천한 사람들
    // 추천인이 "-"인 경우 - 추천인 없음. 혹은 추천인이 대가리
    // enroll의 이름은 참여 순. 즉 어떤 조직원을 추천한 사람은 자신이 추천한 조직원보다 이름이 반드시 먼저 나옴.
    // seller: 판매량 집계 데이터의 판매원 이름 (같은 이름이 있을 수 있음)
    // amount: 판매량 집계 데이터의 판매량
    // 각 판매원이 획득한 이익금을 계산하여 enroll 순서에 따라 나열
    // 조직원 이름은 유일한게 맞는 것 같음. 안 그러면 추천인 구분이 안됨.
    
    // 1. 이름-수익금을 매칭하는 map을 생성한다.
    for(string name: enroll) incomes[name] = 0;

    // 뭔가 parent만 있으면 될 것 같은데?
    // 2. 자신의 부모 이름을 저장하는 map을 생성한다.
    string parent, child;
    for(int i=0; i<referral.size(); i++) {
        child = enroll[i];
        parent = referral[i];
        parents[child] = parent;
    }
    
    // 3. 부모를 거슬러 올라가면서 이익금을 계산한다.
    string seller_name;
    int price;
    double income;
    for(int i=0; i<seller.size(); i++) {
        seller_name = seller[i]; // 판매자 이름
        price = amount[i] * 100; // 판매액
        
        calcIncome(seller_name, price);
    }
    
    // 4. 통계를 계산한다.
    // for(const auto& p: incomes) {
    //     cout << p.first << ": " << p.second << endl;
    // }
    
    vector<int> answer;
    for(string name: enroll) {
        answer.push_back(incomes[name]);
    }
    
    return answer;
}