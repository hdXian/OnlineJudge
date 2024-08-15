n, m = map(int, input().split())

matrix = [([0] * (m+1)) for _ in range(n+1)]

# 첫째 행, 첫째 열은 1로 초기화
for r in range(1, n+1):
    matrix[r][1] = 1

for c in range(1, m+1):
    matrix[1][c] = 1

for r in range(2, n+1):
    for c in range(2, m+1):
        # print("r:%d, c:%d"%(r,c))
        left = matrix[r][c-1]
        above = matrix[r-1][c]
        cross = matrix[r-1][c-1]
        # print("left: %d, above=%d, cross=%d"%(left, above, cross))
        matrix[r][c] = left + above + cross
        # print("matrix[%d][%d] = %d"%(r, c, matrix[r][c]))

bigNum = 10**9 + 7
print(((matrix[n][m] % bigNum) if matrix[n][m] >= bigNum else (matrix[n][m])))