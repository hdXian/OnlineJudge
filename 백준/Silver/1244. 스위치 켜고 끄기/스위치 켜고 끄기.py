# 스위치 개수
n = int(input())

# 스위치
switch = list(map(int, input().split()))

student_num = int(input())

students = []


def changeSwitch(swi, idx):
    swi[idx] = (0 if swi[idx] == 1 else 1)


# 굳이 다 저장할 필요는 없긴 함
for _ in range(student_num):
    student = tuple(map(int, input().split()))
    # print("student:", student)
    students.append(student)


for student in students:
    sex, number = student[0], student[1] # 성별

    if sex == 1: # 남자
        for i in range(number-1, n, number):
            # print("i:", i)
            changeSwitch(switch, i) # switch[i] = (0 if switch[i] == 1 else 1)
        
    else: # 여자
        # 일단 해당 번호 스위치 바꾸고
        changeSwitch(switch, number-1)
        # 최대 대칭 길이 (좌, 우 중 더 짧은 쪽)
        max_sym = min(number-1, n-number)
        # 대칭인 애들 바꾸기
        for i in range(1, max_sym+1): # 1 ~ max_sym+1번 대칭 찾기
            if switch[number-1-i] == switch[number-1+i]:
                changeSwitch(switch, number-1-i)
                changeSwitch(switch, number+i-1)
            else:
                break

# print("switch:", switch, end="")
res = ""
for i in range(n):
    if i != (n-1):
        res += "%d "%switch[i]
    else:
        res += "%d"%switch[i]
    if (i+1)%20 == 0:
        res += "\n"

print(res)
