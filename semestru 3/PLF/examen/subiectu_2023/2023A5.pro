% Candidat(l1...ln):
%               (1)l1, n!=0
%               (2)candidat(l2...ln)
%Candidat(E:Integer,L:List)
%E: elementul generat
%L: lista
% model de flux (i,i)=>determinist,(o,i)=>nedeterminist le vom folosi pe ambele
candidat(E,[E|_]).
candidat(E,[_|T]):-
    candidat(E,T).

%Aranjamente(l1...ln,k,prod):-
%           Aranjamente_helper(l1...ln,k,prod,1,E,[E]) E este candidat(l1...ln)
%Aranjamente(L:List,K:Integer,P:Integer,Col:list)
%L: lista de numere
%K: nr de elem dintr o lista
%P: produsul elementelor dintr o lista
%Col: variabila colectoare
%model de flux(i i i o)=> nedeterminstic
aranjamente(L,K,P,Col):-
    candidat(E,L),
    aranjamente_helper(L,K,P,Col,1,E,[E]).
%aranjamente_helper(l1...ln,k,prod,lungime,prod_lsit,list):
%   list, k==lungime, si prod==prod_list
%   aranjamente_helper(l1..ln,k,prod,lungime+1,prod*E,E U list), unde E este candidat(l1...ln) lungime < k si E nu apartine lui list si prod_list < prod
%aranjamente_helper(L:list,K:Integer,prod:Integer,Col:List,Lg:Integer,P:Integer,List:List)
%L:lista de numere
%K: nr elem dintr o listas
%P: produsul elementelor dintr o listas
%Col: var colectoare
%Lg: lungimea curenta a listei
%P: produsul curent al listei
%List: lista curenta
%model de flux(i i i o i i i)
aranjamente_helper(_,K,Prod,Col,K,Prod,Col).
aranjamente_helper(L,K,Prod,Col,Lg,P,[H|T]):-
    Lg<K,
    candidat(E,L),
    \+ candidat(E,[H|T]),
    Lg1 is Lg+1,
    P1 is P * E,
    aranjamente_helper(L,K,Prod,Col,Lg1,P1,[E,H|T]).

%final(L,K,P,List):
%List: uniunea tuturor Aranjamentelor de L luate cate K cu produsul P
final(L,K,P,List):-
    findall(C,aranjamente(L,K,P,C),List).

