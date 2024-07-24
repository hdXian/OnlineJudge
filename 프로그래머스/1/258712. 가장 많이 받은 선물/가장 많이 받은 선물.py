def solution(friends, gifts):
    matrix = {} # 주고받은 선물
    gift_point = {} # 선물 지수
    month_gift = {} # 이달에 받을 선물
    
    # 초기화
    for row in friends:
        tmp = {}
        gift_point[row] = 0
        month_gift[row] = 0
        for col in friends:
            tmp[col] = 0
            matrix[row] = tmp
    
    for gift in gifts:
        tmp = gift.split()
        # 1이 2에게 선물을 줌
        first, second = tmp[0], tmp[1]
        matrix[first][second] += 1
        # 선물 지수 계산
        gift_point[first] += 1
        gift_point[second] -= 1
    
    
    # 1. 두 사람 간에 선물을 주고받은 기록을 비교한다.
    for row in matrix:
        for col in matrix[row]:
            if row == col: # 자기 자신은 생략
                continue
            # 2. 선물을 더 많이 받은 쪽이 적게 받은 쪽에게 다음달 선물을 준다.
            if matrix[row][col] > matrix[col][row]:
                month_gift[row] += 1
            # 3. 주고받은 선물이 같다면 선물 지수가 더 작은 쪽이 큰 쪽에게 선물을 준다.
            elif matrix[row][col] == matrix[col][row]:
                if gift_point[row] > gift_point[col]:
                    month_gift[row] += 1
                
    # 4. 다음달에 선물을 가장 많이 받는 사람이 선물을 받는 수를 구한다.
    max_gift = max(list(month_gift[row] for row in month_gift))
    return max_gift
