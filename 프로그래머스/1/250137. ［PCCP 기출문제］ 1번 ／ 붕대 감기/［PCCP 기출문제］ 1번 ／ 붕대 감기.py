def solution(bandage, health, attacks):
    t, x, y = map(int, bandage)
    hp = health
    crnt_time = 0
    
    for attack in attacks:
        atk_time, atk_dmg = attack[0], attack[1]
        
        timer = 0
        for i in range(atk_time - crnt_time - 1):
            timer += 1
            if hp == health:
                continue
                
            hp += x
            
            if timer == t:
                hp += y
                timer = 0
            
            if hp > health:
                hp = health

        hp -= atk_dmg
        crnt_time = atk_time
        if hp <= 0:
            return -1
              
    answer = hp
    return answer