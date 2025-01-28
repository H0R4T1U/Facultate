lungime(N,[H|T]):-
    lungime(N1,T),
    N is N1+1.
lungime(0,[]).
% candidat(l1...ln)=> l1, n != 0
%                  => candidat(l2....ln)
candidat(E,[E|_]).
candidat(E,[_|T]):-
    candidat(E,T).
% combinari(L1...ln, lungime):-
%   combinari_helper(l1,n,1,e), unde e=candidat(l1...ln)
combinari(L,N,C):-
    candidat(E,L),
    combinari_helper(L,N,C,1,[E]).

% combuinri_helper(List,n,lg,sol)
%   sol, n=lg
%   combinari_helper(List,n,lg+1,E U sol),unde E = candidat(E,L)
combinari_helper(_,N,C,N,C).
combinari_helper(L,N,C,Lg,[H|T]):-
    Lg<N,
    candidat(E,L),
    E<H,
    Lg1 is Lg+1,
    combinari_helper(L,N,C,Lg1,[E,H|T]).

final(L,Lista):-
    lungime(N,L),
    findall(C,combinari(L,N,C),[H|T]),
    Lista = [[],[H|T]].


