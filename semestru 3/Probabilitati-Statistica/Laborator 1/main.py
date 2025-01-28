from random import sample
from math import perm,comb
from itertools import permutations,combinations,combinations_with_replacement



def prob2(word):
    permutari = list(permutations(word,len(word)))

    for i in permutari:
        print(i)
    print(f"nr permutari:{len(permutari)}")

    print(sample(permutari,1))
def aranjamente(word,nr,total=False,aleator=False):
    aranjamente = list(permutations(word,nr))
    if(total):
        print("len")
        print(len(aranjamente))
        return
    if(aleator):
        print("sample")
        print(sample(aranjamente,1))
        return
    for i in aranjamente:
        print(i)

def combinari(word,nr,total=False,aleator=False):
    comb = list(combinations(word,nr))
    if(total):
        print("len:")
        print(len(comb))
        return
    if(aleator):
        print("sample:")
        print(sample(comb,1))
        return
    for i in comb:
        print(i)

def combinari_cu_repetitie(word,len):
    comb_cu_rep = combinations_with_replacement(word,len)
    for i in comb_cu_rep:
        print("".join(i))

def main():
    # PROBLEMA 2
    prob2("word")
    # PROBLEMA 3
    aranjamente("word",2)
    aranjamente("word",2,total=True)
    aranjamente("word",2,aleator=True)
    combinari("word",2)
    combinari("word",2,total=True)
    combinari("word",2,aleator=True)
    #PROBLEMA 4
    combinari_cu_repetitie("ABCDE",4)
    # PROBLEMA 5
    aranjamente([1,2,3,1,1,1,1,1],5,total=True)

main()