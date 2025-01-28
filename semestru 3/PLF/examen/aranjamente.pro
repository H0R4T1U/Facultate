%Candidat(l1... ln):
%          1 l1, Daca n != 0
%          2. Candidat(l2...ln)
%Candidat(E:Integer,L:lista):
%E=>elementul care se genereaza
%L=> lista din care se genereaza elementul e
%model de flux (o,i) => nedeterministic
%              (i,i) => deterministic, le vom folosui pe ambele
candidat(E,[E|_]).
candidat(E,[_|T]):-
    candidat(E,T).
% Aranjamente(l1...ln,k):
%           Aranjamente_helper(l1...ln,k,1,E), E=Candidat(l1...ln)
%Aranjamente(L:List,K:Integer,Col:List):
%L: lista data
%K: K de la aranjamente
%Col: Lista colectoare
% Model de flux (i i o)
aranjamente(L,K,Col):-
    candidat(E,L),
    aranjamente_helper(L,K,Col,1,[E]).
%Aranjamente_helper(l1...ln,K,lung,list):
%                   1.list, daca lung=k
%                   2. aranjamente_helper(l1...ln,k,lung+1,E U list), unde E = Candidat(l1...ln) Lg<K,Candidat(l1...ln) nu apartine listei List
%Aranjamente_helper(L:List,K:Integer,Col:List,Lg:Integer,List:List)
%L: lista initiala
%K: K de la Aranjamente
%Col: Variabila colectoare
%Lg: lungimea secventei actuale
%List: secventa actuala
aranjamente_helper(_,K,Col,K,Col).
aranjamente_helper(L,K,Col,Lg,[H|T]):-
    Lg<K,
    candidat(E,L),
    \+ candidat(E,[H|T]),
    Lg1 is Lg + 1,
    aranjamente_helper(L,K,Col,Lg1,[E,H|T]).


%Final(L1..ln,k,List):-
%   Uniunea tuturor listelor returnate de aranjamente(L1...ln,K)
%Final(L:List,K:integer,List:lista):-
%L: lista initiala
%K: k-ul de la aranjamente
%List:lista in care se stocheaza
final(L,K,List):-
    findall(Col,aranjamente(L,K,Col),List).
