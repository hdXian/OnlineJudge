from collections import deque

def solution(n, t, m, timetable):
    
    def add_minute(hour, minu, add_m):
        total_minu = hour * 60 + minu
        total_minu += add_m
        hour = total_minu // 60
        minu = total_minu % 60
        return (hour, minu)
    
    times = [] # (h, m) 튜플을 가지는 리스트
    for time in timetable:
        tmp = time.split(":")
        hour = int(tmp[0])
        minute = int(tmp[1])
        times.append((hour, minute))
        
    times.sort()
    times_q = deque(times)
    
    answer_time = None
    for i in range(0, n):
        bus_time = add_minute(9, 0, i * t)
        bus_hour = bus_time[0]
        bus_minute = bus_time[1]
        res_seats = m
        popped = None
        # print("버스 도착:", f"{bus_hour}시 {bus_minute}분")
        # print("승객 리스트:", times_q)
        for j in range(m):
            if len(times_q) > 0:
                if times_q[0][0] < bus_hour:
                    popped = times_q.popleft()
                    # print(popped, "승객 탑승")
                    res_seats -= 1
                elif times_q[0][0] == bus_hour:
                    if times_q[0][1] <= bus_minute:
                        popped = times_q.popleft()
                        # print(popped, "승객 탑승")
                        res_seats -= 1
                    else:
                        pass
                else:
                    break
            else: # if len(times) > 0
                # print(f"승객 탑승 완료, 남은자리 {res_seats}")
                pass
        if len(times_q) == 0: # 승객이 없으면 막차를 타고 간다
            # print("더이상 승객이 없음. 막차를 타고 가야함")
            if res_seats > 0:
                answer_time = add_minute(9, 0, (n-1) * t)
                break
            else: # 근데 승객이 딱 맞게 다 탔으면 마지막 사람보다는 1분 일찍 와야함
                last = popped
                print("last:", last)
                answer_time = add_minute(last[0], last[1], -1)
        elif i == (n-1): # 승객이 계속 남아있으면 최소한 막차는 타고 가야한다
            # print("승객이 계속 남음. 막차를 잡아 타야함")
            if res_seats > 0: # 남은 자리가 있으면 막차 시간에 맞춰서 온다
                answer_time = add_minute(9, 0, (n-1) * t)
                # print("막차에 자리가 남음:", answer_time)
            else: # 남은 자리가 없으면 가장 늦게 온 사람보다는 1분 일찍 와야한다
                last = popped
                print("last:", last)
                answer_time = add_minute(last[0], last[1], -1)
                
        # print(f"승객 탑승 완료, 남은자리 {res_seats}")
        # print("버스 출발")
                    
    # print("answer_time:", answer_time)
    answer = ("%02d:%02d" % (answer_time[0], answer_time[1]))
    # print(answer)
    return answer

