%Candidat(L1...ln):
%       l1, n!=0
%       Candidat(l2...ln)
%Candidat(E:Integer,L:list)
%E: elementul generat
%L: lista din care se genereaza
%model de flux (i,i) => deterministic, si (o,i) nedet, le vom folosi pe ambele s
candidat(E,[E|_]).
candidat(E,[_|T]):-
    candidat(E,T).

%Permutari(l1...ln,n):
%           Permutari_helper(L1....ln,n,1,E), E=candidat(l1...ln)
%Permutari(L:list,N:integer,Col:List)
%List:lista de generat permutari
%N: lungimea listei
%Col: Variabila colectoare
permutari(L,N,Col):-
    candidat(E,L),
    permutari_helper(L,N,Col,1,[E]).
%permutari_helper(l1...ln,n,lung,list):-
%                   list:- n==lung
%                   permutari_helper(l1....ln,n,lung+1,E U List), daca lung<n,E nu apaertine lui List
%permutari_helper(L:List,n:Integer,Col:List,lung:Integer,List:list)
%calculeaza permutarile(i i o i i)
%L:lista cu nr
%N: lungimea listei
%Col: var colectoare
%Lung: lung secv
%List: secv
permutari_helper(_,N,Col,N,Col).
permutari_helper(L,N,Col,Lg,[H|T]):-
    Lg<N,
    candidat(E,L),
    \+ candidat(E,[H|T]),
    Lg1 is Lg + 1,
    permutari_helper(L,N,Col,Lg1,[E,H|T]).

%Lungime(l1...ln):-
%           0, n==0
%           1 + lungime(l2...ln)
%Lungime(N:Integer,L:List)
%L lista la care se calculeaza lungimea
%N: lungimea
%Model de flux (i,i) => determinsitic, (O,i) tot deterministic,il vom folosi
lungime(0,[]).
lungime(N,[_|T]):-
    lungime(N1,T),
    N is N1+1.

%final(l1...ln):-
%           permutari(l1...ln,lungime), unde lungime=lungime(l1...ln)
%final(L,List):-
%L:lista initiala
%List:lista uniunii tuturor listelor de permutari
final(L,List):-
    lungime(N,L),
    findall(Col,permutari(L,N,Col),List).