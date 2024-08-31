p = int(input())

cases = []
result = []

for _ in range(p):
    cases.append(list(map(int, input().split()))[1:])

for test_case in cases:
    line = []
    total_move = 0
    for i in range(len(test_case)): # 테스트 케이스 순서대로 아이들이 줄을 섬
        child = test_case[i]
        move = 0
        # 자기보다 큰 학생 중 가장 앞에 있는 사람 앞에 섬.
        while (move < i) and (line[move] < child):
            move += 1
        line.insert(move, child)
        total_move += i-move
    
    result.append(total_move)

for i in range(p):
    print("%d %d"%((i+1), result[i]))

