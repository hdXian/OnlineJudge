p = [0, 1, 1, 1, 2, 2] +  [0 for _ in range(101-6)] # 0 안씀. 1~100 인덱스.

# 테스트 케이스 개수
t = int(input())
cases = []

for _ in range(t):
    tmp = int(input())
    cases.append(tmp)

for i in range(6, max(cases)+1):
    p[i] = p[i-1] + p[i-5]

result = ""

for c in cases:
    result += "%d\n"%p[c]

print(result)
