computers = int(input())
links = int(input())

network = [[] for _ in range(computers+1)]
infected = [False for _ in range(computers+1)]

for _ in range(links):
    src, dst = map(int, input().split())
    network[src].append(dst)
    network[dst].append(src)

count = 0

def warm(n):
    global count
    infected[n] = True
    count += 1
    for com in network[n]:
        if infected[com] == False:
            warm(com)

warm(1)

print(count-1)
