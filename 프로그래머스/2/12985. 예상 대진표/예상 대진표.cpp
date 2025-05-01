#include <iostream>
#include <cmath>

using namespace std;

int solution(int n, int a, int b)
{
    int round = 1;
    double da = a;
    double db = b;
    da = ceil(da/2);
    db = ceil(db/2);
    
    while (da != db) {
        da = ceil(da/2);
        db = ceil(db/2);
        round++;
    }
    
    int answer = round;

    return answer;
}