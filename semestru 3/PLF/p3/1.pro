% 6. sa se genereze toate sirurile de n paranteze ce se inchid corect 
% exemplu: n=4 sunt 2 solutii: ()() (())

% MODEL MATEMATIC RECURSIV:

% paranteze(deschis,inchis,n)
% ( + paranteze(deschis+1,inchis,n), daca deschis < n
% ) + paranteze(deschis,inchis+1,n), daca inchis < deschis && inchis < n
generare(N,Rez):-
    paranteze(0,0,N,Rez).
paranteze(Deschis, Inchis, N,[T]) :-
    Deschis < N, 
    NewDeschis is Deschis + 1,
    H = '(',
    paranteze(NewDeschis, Inchis, N,[H|T]).

paranteze(Deschis, Inchis, N,[H1|T]) :-
    Inchis < Deschis,
    Inchis < N,
    NewInchis is Inchis + 1,
    H2 = ')',
    paranteze(Deschis, NewInchis, N,[H1,H2|T]).

paranteze(N, N, N,Rez).
