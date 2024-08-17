# 물건을 정확히 n 킬로그램 배달.
n = int(input())
# print("n:", n)

# 가장 적은 봉지 수로 물건을 포장
# 봉지는 5kg짜리, 3kg짜리가 있음.

def check(num):
    count = -1
    max_5 = num // 5
    # print("max_5:", max_5)
    for i in range(max_5, -1, -1):
        # print("i:", i)
        tmp_count = i # 5kg 봉지 수
        rest = num - (5*i) # 3kg으로 처리해야 할 무게
        # print("rest:", rest)
        if rest%3 == 0:
            tmp_count += rest//3
            count = tmp_count
            return count
    return count # 안되면 -1 리턴


# 3 <= n <= 5000
# memo = [0, 0, 0, 1, -1, 1, 2, -1, 2, 3, 2, 3, 4, 3, 4, 3]

result = check(n)
# print("result:", result)
print(result)
