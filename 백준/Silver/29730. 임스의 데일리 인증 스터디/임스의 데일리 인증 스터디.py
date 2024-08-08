import re

# boj.kr 정확히 매칭 (.은 이스케이프 문자 사용해야함)
# d+: 하나 이상의 숫자
pattern = re.compile(r'boj\.kr/\d+')

# 첫 번쨰 기준 문자열 길이, 두번째 기준 문자 (알파벳순)
def lenAndAlpha(word):
    return (len(word), word)

n = int(input())

records = []
bojs = []

for _ in range(n):
    record = input()
    match = pattern.search(record)
    if match:
        bojs.append(record)
    else:
        records.append(record)


records.sort(key=lenAndAlpha)
bojs.sort(key=lenAndAlpha)

# print("records:", records)
# print("bojs:", bojs)

res = records + bojs

for r in res:
    print(r)

