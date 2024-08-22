# n일동안 운동, 하루 k만큼 중량 감소
n, k = map(int, input().split())

kits = list(map(int, input().split()))

count = 0

def check(weight, kits):
    # print("check() weight=%d, kits="%weight, kits)
    global count
    if len(kits) == 0:
        count += 1
    
    for kit in kits:
        # print(kit)

        crnt_weight = weight+kit-k

        if crnt_weight < 500:
            # print("failed: kit=%d, crnt_weight=%d"%(kit, crnt_weight))
            continue
        else:
            temp = kits.copy()
            temp.remove(kit)
            check(crnt_weight, temp)
    

check(500, kits)
print(count)
