n = int(input())

# 각 도시로 넘어갈 때마다의 최소 비용을 구해서 더하면 될듯.
# 어차피 다음 도시로 가려면 최소한 지금 도시나 이전까지의 도시들에서 기름을 구해와야 함.

distances = list(map(int, input().split()))
oil_prices = list(map(int, input().split())) # 마지막 도시의 연료값은 필요없긴 함

# print("n:", n)
# print("distances:", distances)
# print("oil_prices:", oil_prices)

# print(distances[:2])

total_price = 0
min_price = oil_prices[0]
# 각 도로를 지날 때마다
# 1번째 도로의 기름 수급처: 1번 도시
# n번째 도로의 기름 수급처: 1, 2, 3, ..., n번째 도시
for i in range(n-1): # i는 인덱스
    # print("i:", i)
    distance = distances[i]
    if oil_prices[i] < min_price:
        min_price = oil_prices[i]
    total_price += distance * min_price

# print("total_price:", total_price)
print(total_price)
