t = int(input())



res = []

tmp = 1


def facto(k):
    if k <= 1:
        return 1
    else:
        return k * facto(k-1)


for i in range(t):
    n, m = map(int, input().split())
    tmp = facto(m) // (facto(m-n) * facto(n))
    res.append(tmp)


for r in res:
    print(r)






