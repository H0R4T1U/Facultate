% Modelul Matematic Recursiv
% inserare([e2],E,i):
%	  inserare([E+e2],E,i-1), i != 0
%	  [], i = 0
% func(e1 e2 ... en,Elem,poz,i):
%	  e1 + func(e2,... en,Elem, poz+1,i),poz != 2 * i + 1
%	  e1 + inserare(e2,e,i) + func(e3...en,Elem,poz+1,i+1), poz = 2*i+1
%	  [], n = 0 
% run(e1 e2 ... en,Elem):-
%   func(e1 e2 ... en,Elem, 1,0)
inserare(Elem,I,Rez):-
e  I =\= 0,
  I1 is I-1,
	Rez = [Elem|T],
	inserare(Elem,I1,Rez).

func([H|T],Elem,Poz,I,Rez):-
	Poz =\= 2*I+1,
	Poz1 = Poz +1,
	func([T],Elem,Poz1,I,Rez1),
	Rez = [H|Rez1].
func([H1,H2|T],Elem,Poz,I,Rez):-
	Poz =:= 2 * I +1,
	Poz1 is Poz + 1,
	I1 = I +1,
	func([T],Elem,Poz1,I1,Rez1),
	inserare(Elem,I,[H2]),
	Rez = [[H,Rez2]|Rez1].
	
func([],Elem,Poz,I,Rez).

run(List,Elem,Rez):=
  func(List,Elem,1,0,Rez).
