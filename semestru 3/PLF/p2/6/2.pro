%Se da o lista eterogena, formata din numere intregi si liste de numere intregi. 
%Se cere ca in fiecare sublista sa se scrie de 2 ori fiecare numar prim. De ex:
%[1, [2, 3], 4, 5, [1, 4, 6], 3, [1, 3, 7, 9, 10], 5] =>
%[1, [2, 2, 3, 3], 4, 5, [1, 4, 6], 3, [1, 3, 3, 7, 7, 9, 10], 5]


% Model Matematic Recursiv
% Dublare_prim_sublista(l1, l2 ... ln){
%  [], n == 0
%  dublare_prim(l1) (+) dublare_prim_sublista(l2, ... ln), l1 este lista si n > 1
%  l1 (+) dublare_prim_sublista(l2,.... ln),  l1 nu este lista si n > 1
%  dublare_prim(l1), l1 este lista si n = 1
%  l1, l1 nu este lista si n = 1
%}
%
% Este_prim(N,k){
%   0, n <=1
%   0, n %k == 0 && k * k < n
%   1, k *k > n
%   Este_prim(n,k+1), altfel, k*k < nx
%}
%
% Dublare_prim(l1 l2 ... ln):
%   multimea_vida, n ==0
%   l1 (+) l1 (+) Dublare_prim(l2 ... ln), Este_prim(l1,2) si n > 1
%   l1 (+) Dublare_prim(l2 ... ln), altfel, ! este_prim(l1,2) si n >1
%   l1 (+) l1, este_prim(l1,2) si n = 1
%   l1, altfel, !este_prim(l1,2) si n = 1



%
%este_prim(N:int, K:int)
% Model de flux (i, i)
% N nr de verificat
% K index curent de verificare
este_prim(N, K) :-
    N > 1,                       % Verificăm dacă N este mai mare decât 1
    ( K * K > N -> true ;        % Dacă K depășește sqrt(N), N este prim
      ( N mod K =:= 0 -> false ; % Dacă N este divizibil cu K, nu este prim
        K1 is K + 1,             % Incrementăm K
        este_prim(N, K1)          % Continuăm verificarea cu K+1
      )
    ).


% dublare_prim(L:list, Rez:lIST)
% Model de flux: (i,o)
% L: lista initiala
% Rez lista dupa dublarea nr prime
dublare_prim([],[]). % daca e gol returneaza lista []
dublare_prim([H1],Rez):- % Cazul in care mai este un singur element si este prim
    este_prim(H1,2),
    Rez = [H1,H1].
dublare_prim([H1],Rez):- % daca nu este prim
    \+ este_prim(H1,2),
    Rez = [H1].
dublare_prim([H1,H2|T],Rez):-% caz general daca este prim
    este_prim(H1,2),
    dublare_prim([H2|T],Rez1),
    Rez = [H1,H1|Rez1].
dublare_prim([H1,H2|T],Rez):-% caz general daca nu este prim
    \+ este_prim(H1,2),
    dublare_prim([H2|T],Rez1),
    Rez = [H1|Rez1].

%dublare_prim_sublista(Lista:list,Rez:list)
%Model de flux(i,o)
%Lista: lista initiala
% Rez: lista dupa dublarea tuturor nr prime din subliste

%TESTE
%dublare_prim_sublista([1, [2, 3], 4, 5, [1, 4, 6], 3, [1, 3, 7, 9, 10], 5],Rez) =>
%Rez=[1, [2, 2, 3, 3], 4, 5, [1, 4, 6], 3, [1, 3, 3, 7, 7, 9, 10], 5]
dublare_prim_sublista([],[]). % Caz lista goala
dublare_prim_sublista([H1],Rez):- % Cazul in care mai este un singur element si este lista
    is_list(H1),
    dublare_prim(H1,L_dublat),
    Rez = [L_dublat].
dublare_prim_sublista([H1],Rez):- % cazul in care mai este un singur element si nu este lista
    \+ is_list(H1),
    Rez = [H1].
dublare_prim_sublista([H1,H2|T],Rez):-
    is_list(H1), % Verificam daca elementul este o lista
    dublare_prim(H1,L_dublat), % dublam nr prime din lista
    dublare_prim_sublista([H2|T],Rez1), % mergem in continuare
    Rez = [L_dublat | Rez1]. % la rezultat adaugam lista cu nr prime dublate in fata celorlalte care vin din adancime
dublare_prim_sublista([H1,H2|T],Rez):-
    \+ is_list(H1), % daca nu este o lista
    dublare_prim_sublista([H2|T],Rez1), % mergem in adancime
    Rez = [H1 | Rez1]. % adaugam elementul in fata celor care vin din adancime1