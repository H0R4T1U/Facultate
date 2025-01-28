% candidat(E:integer, L:list)
% E : elementul care se genereaza
% L : lista din care generam
% model de flux: (o,i) nedeterminist, pe care il folosim
% dar functioneaza si in (i,i) determinist
% model matematic: candidat(L) = (1) L1, daca L nevida
%                              = (2) candidat(L2..Ln)
candidat(E,[E|_]).
candidat(E,[_|T]):-
	candidat(E,T).
% lungime(L:list, Lg:integer)
% L : lista careia ii calculam lungimea
% Lg: rezultatul apelului
% model de flux (i,o) determinist, pe care il folosim
% dar functioneaza si in (i,i) determinist
% model matematic: lungime(L1..Ln) = 0, daca n=0
%				   = 1+lungime(L2..Ln), altfel
lungime([],0).
lungime([_|T],Lg):-
	lungime(T,Lg1),
	Lg is Lg1+1.
% submultimi(L:list, N:integer, C:list)
% L : lista ale carei submultimi le generam
% N : lungimea listei
% C : colectoarea care contine submultimea generata
% model de flux: (i,i,o) nedeterminist, dar functioneaza si
% in (i,i,i) determinist
% model maetmatic: submultimi(L,N) = (1) submultimi_h(L,N,1,E,[E]), unde
%                                     E=candidat(L)
submultimi(L,N,C):-
	candidat(E,L),
	submultimi_h(L,N,C,1,E,[E]).
%submultimi_h(L:list, N:integer, C:list, Lg:integer, Suma:integer,Lista: list)
% L : lista ale carei submultimi le generam
% N : lungimea listei
% C : variabila coelctoare
% Lg: lungimea submultimii curente
% Suma: suma submultimii curente
% Lista: submultimea curenta
% model de flux: (i,i,o,i,i,i) nedeterminist, dar functioneaza si in
% (i,i,i,i,i,i)
% model matematic: submultimi_h(L,N,Lg,Suma,Lista) =
% (1) Lista, daca Suma este para
% (2) submultimi_h(L,N,Lg+1,Suma+E, E(+)Lista)
% unde E=candidat(L) si Lg<N, E<H
submultimi_h(_,_,C,_,Suma,C):-
	Suma mod 2 =:= 0.
submultimi_h(L,N,C,Lg,Suma,[H|T]):-
	Lg < N,
	candidat(E,L),
	E<H,
	Lg1 is Lg+1,
	Suma1 is Suma+E,
	submultimi_h(L,N,C,Lg1,Suma1,[E,H|T]).
%final(L:list, Lista:list)
% L : lista initiala
% Lista : lista de submultimi generata
% model de flux: (i,o) determinist pe care il folosim,
% dar functioneaza si in (i,i) determinist
% model matematic:
% final(L) = U tuturor C-urile generate submultimi(L,lungime(L),C)
final(L,Lista):-
	lungime(L,N),
	findall(C,submultimi(L,N,C),Lista),
	Lista=[[],H|T].