import math
from random import randint
from numpy import arange
from matplotlib.pyplot import plot, grid, title, show
from matplotlib.pyplot import axis, plot, show
from random import random
from math import dist
def same_birthday(nr_pers,nr_sim):
    fav = 0
    for i in range(nr_sim):
        persoane = []
        for i in range(nr_pers):
            persoane.append(randint(1, 365))
        if len(set(persoane)) != nr_pers:
            fav+=1
    return fav/nr_sim

#
# n = int(input("Nr persoane:"))
# nr_sim = int(input("Nr sim:"))
# fav = 0
#
#
# def same_birthday2(nr_pers):
#     p = 1 # prob ca nr de pers sa aiba zile de nastere diff
#     for i in range(nr_pers):
#         p=p*(365-i)/362
#
#     return 1-p
#
# x = range(1,50)
# y=[same_birthday(n,nr_sim) for n in x]
# plot(x,y,"ro")
# yticks=arange(0,1.1,0.1)
# xticks = arange(0,55,5)
# title("Estimari din simulari")
# grid()
# show()


def patrat(nr_pct):
    count = 0
    for i in range(nr_pct):
        F=[random(),random()]
        if dist(F,E) < 0.5:
            count+=1
            plot(F[0],F[1],"ro")
        else:
            plot(F[0],F[1],'bo')
    show()
    return count/nr_pct
# print(f"Frecventa:{patrat(5000)}")
axis("square")
A,B,C,D=[0,0],[1,0],[0,1],[1,1]
E=[0.5,0.5]
def patrat2(nr_pct):
    count = 0
    for i in range(nr_pct):
        F=[random(),random()]
        if dist(F,E) < min(dist(F,A),dist(F,B),dist(F,C),dist(F,D)):
            count+=1
            plot(F[0],F[1],"ro")
        else:
            plot(F[0],F[1],'bo')
    show()
    return count/nr_pct

print(f"Frecventa:{patrat2(5000)}")