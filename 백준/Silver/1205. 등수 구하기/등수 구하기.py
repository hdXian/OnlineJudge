# n: 현재 랭킹에 올라가 있는 점수의 개수 0 <= n <= p
# my_score: 새로 입력될 점수 0 <= my_score <= 2,000,000,000 (20억)
# p: 랭킹 리스트에 올라가는 점수의 개수 10 <= p <= 50
n, my_score, p = map(int, input().split())
# print(n, my_score, p)

ranking = None

if n == 0:
    ranking = []
else:
    ranking = list(map(int, input().split()))

# print(ranking)
ranking.sort(reverse=True) # 내림차순으로 정렬
# print(ranking)

# 랭킹 리스트가 100, 90, 90, 80 -> 1, 2, 2, 4등
my_rank = -1

def getMyRank(score, arr):
    rank = -1
    # 중복 점수가 있을 경우 동점 중 가장 뒤
    if arr.count(score) != 0:
        idx = arr.index(score)
        while arr[idx] != score:
            idx += 1
        rank = idx
    # 없으면 그냥 순위 계산
    else:
        for i in range(len(arr)):
            if arr[i] < score:
                rank = i
                break
    return rank # returns index


# 랭킹 리스트가 꽉 차있을 때
if n==p:
    # 랭킹 최하점보다 낮거나 같으면 바로 -1
    if my_score <= min(ranking):
        my_rank = -1
    # 현재 점수보다 낮은 점수가 랭킹에 있다면 해당 랭킹을 차지함
    # 같으면? 뒤에꺼 하나 밀어내야 함
    else:
        my_rank = getMyRank(my_score, ranking) + 1
# 랭킹 리스트가 꽉 차있지 않을 때
else:
    ranking.append(my_score)
    ranking.sort(reverse=True)
    my_rank = getMyRank(my_score, ranking) + 1


print(my_rank)

