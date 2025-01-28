f([],-1).
f([H|T],S):-H>0,f(T,S1),S1<H,!,S is H.
f([_|T],S):-f(T,S1),S is S1.

c([],-1).
c([H|T],S):-c(T,S1),aux(S1,H,S).
aux(S1,H,S):-
    H>0,
    S1<H,
    !,
    S is H.
aux(S1,_,S):-
    S is S1.
