n = int(input())

# 배열 A를 움직여 a[n] * b[n]의 결과가 가장 작도록 만들 것.
arr_A = list(map(int, input().split()))
arr_B = list(map(int, input().split()))

# 배열 A를 오름차순으로 정렬한다.
arr_A.sort()

# 배열 B를 기준으로 (값, 순서)를 가진 튜플 배열을 만든다.
tup_arr_b = []
for i in range(n):
    tmp = (arr_B[i], i)
    tup_arr_b.append(tmp) # (값, 순서)

# 튜블 배열 B를 값을 기준으로 내림차순 정렬한다.
tup_arr_b.sort(key=lambda tup:tup[0], reverse=True)

# 배열 B의 가장 큰 요소와 A의 가장 작은 요소를 차례대로 매칭한다.
result_arr_a = [0] * n

for i in range(n):
    idx = tup_arr_b[i][1] # 배열 B에서 가장 큰 값의 인덱스
    result_arr_a[idx] = arr_A[i]

# S 값을 구한다.
s = 0
for i in range(n):
    s += result_arr_a[i] * arr_B[i]

print(s)
