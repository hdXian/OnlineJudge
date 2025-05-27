#include <string>
#include <vector>

using namespace std;

int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
    // 초침은 초당 1만큼 갈 수 있음
    // 분침은 초당 1/60 만큼 갈 수 있음
    // 시침은 초당 1/(60*12) 만큼 갈 수 있음
    
    // 실수 연산을 하지 않도록 단위를 바꿔본다.
    // 시침이 초당 1만큼 간다고 했을 때..
    // 초침은 초당 720만큼 갈 수 있음
    // 분침은 초당 12만큼 갈 수 있음 (분당 720)
    // 시침은 초당 1만큼 갈 수 있음 (분당 60, 시간당 3600)
    // 그럼 한바퀴의 총 거리는? 720 * 60 = 43200 거리로 볼 수 있음.
    
    int start_sec = h1*3600 + m1*60 + s1;
    int end_sec = h2*3600 + m2*60 + s2;
    
    int s_tick = s1 * 720;
    int m_tick = (m1*60 + s1) * 12;
    int h_tick = (h1%12 * 3600) + m1*60 + s1;
    
    int pre_s, pre_m, pre_h;
    
    int alarm = 0;
    
    if (s_tick == m_tick || s_tick == h_tick) alarm++;
    
    while(start_sec < end_sec) {
        pre_s = s_tick;
        pre_m = m_tick;
        pre_h = h_tick;
        
        s_tick += 720;
        m_tick += 12;
        h_tick += 1;
        
        if ((s_tick == m_tick) || ((pre_s < pre_m) && (s_tick > m_tick))) alarm++;
        if ((s_tick == h_tick) || ((pre_s < pre_h) && (s_tick > h_tick))) alarm++;
        
        if (s_tick == 43200) s_tick = 0;
        if (m_tick == 43200) m_tick = 0;
        if (h_tick == 43200) h_tick = 0;
        
        start_sec++;
    }
    
    // 12시 정각에 중복해서 울리는 경우를 뺀다.
    if (h1 < 12 && h2 >= 12) alarm--;
    
    return alarm;
}