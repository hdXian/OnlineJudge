n = int(input())

def check(num):
    count = -1
    max_5 = num // 5
    for i in range(max_5, -1, -1):
        tmp_count = i
        rest = num - (5*i)
        if rest%3 == 0:
            tmp_count += rest//3
            count = tmp_count
            return count
    return count

print(check(n))
