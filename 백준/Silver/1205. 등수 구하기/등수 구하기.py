n, my_score, p = map(int, input().split())

ranking = None

if n == 0:
    ranking = []
else:
    ranking = list(map(int, input().split()))

ranking.sort(reverse=True)

my_rank = -1

def getMyRank(score, arr):
    rank = -1
    if arr.count(score) != 0:
        idx = arr.index(score)
        while arr[idx] != score:
            idx += 1
        rank = idx
    else:
        for i in range(len(arr)):
            if arr[i] < score:
                rank = i
                break
    return rank

if n==p:
    if my_score <= min(ranking):
        my_rank = -1
    else:
        my_rank = getMyRank(my_score, ranking) + 1
else:
    ranking.append(my_score)
    ranking.sort(reverse=True)
    my_rank = getMyRank(my_score, ranking) + 1

print(my_rank)
