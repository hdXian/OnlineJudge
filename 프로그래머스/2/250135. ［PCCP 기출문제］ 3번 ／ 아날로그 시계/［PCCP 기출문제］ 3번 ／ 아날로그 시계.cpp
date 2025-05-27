#include <string>
#include <vector>

using namespace std;

// 0시 0분 0초부터 total_sec의 시간이 흐를 때까지 울린 알람의 총 횟수를 계산
int calcAlarm(int total_sec) {
    
    // 분침이 한바퀴 도는 동안 (시간동안) 초침은 60바퀴 돈다.
    // 분침은 한바퀴 도는 동안 초침과 59번 마주친다.
    // (59분에서 0분으로 넘어가는 정각 타이밍은 이번 바퀴에서 카운트되지 않기 때문)
    // 즉 분침과 초침은 1시간에 59번 마주친다. -> 3600초에 59번 마주친다. 
    // -> 1초에 59/3600 번 마주친다. -> x초에 x * (59/3600) 번 마주친다.
    int min_count = total_sec * 59 / 3600; // 분침과 초침이 만나는 횟수
    
    // 시침이 한바퀴 도는동안 (12시간 동안) 초침은 720바퀴(12*60) 돈다.
    // 시침은 한바퀴 도는동안 초침과 719번 마주친다.
    // (역시 11시 59분에서 12시 정각으로 넘어가는 타이밍은 이번 바퀴에서 카운트하지 않기 때문)
    // 시침과 초침은 12시간에 719번 마주친다. -> 43200초에 719번 마주친다.
    // 1초에 719/43200번 마주친다 -> x초에 x * (719/43200) 번 마주친다.
    int hour_count = total_sec * 719 / 43200;
    
    int total_count = min_count + hour_count;
    
    // 00시 정각, 12시 정각에는 분침과 시침이 겹치므로 알람 횟수를 빼줘야 한다.
    total_count--; // 00시 정각의 경우
    int hour = total_sec / 3600;
    if (hour >= 12) total_count--; // 시간이 12시보다 늦을 경우, 12시 정각에 대한 카운트도 뺀다.
    
    return total_count;
}

int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
    
    // 1. 시작 시간과 끝 시간을 초 단위로 환산한다.
    int start_sec = h1*3600 + m1*60 + s1;
    int end_sec = h2*3600 + m2*60 + s2;
    
    // 2. 00시 00분 00초를 시작으로, (끝 시간까지 울린 알람) - (시작 시간까지 울린 알람)을 계산한다.
    int answer = calcAlarm(end_sec) - calcAlarm(start_sec);
    
    // 3. 시작 시간 자체가 알람이 울리는 시간인 경우, 이를 추가해준다. (00시, 12시)
    if (start_sec == 0 || (h1==12 && m1==0 && s1==0)) answer++;
    
    return answer;
}