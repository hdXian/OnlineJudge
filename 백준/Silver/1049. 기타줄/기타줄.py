n, m = map(int, input().split())

packs = []
ones = []

for i in range(m):
    pack, one = map(int, input().split())
    packs.append(pack)
    ones.append(one)


low_pack = min(packs)
low_one = min(ones)

pack_num = n//6
one_num = n%6

pack_price = 0
one_price = 0

if low_pack > low_one*6:
    pack_price = pack_num * (low_one*6)
else:
    pack_price = pack_num * low_pack

if one_num * low_one > low_pack:
    one_price = low_pack
else:
    one_price = one_num * low_one

result = pack_price + one_price

print(result)

