% model de flux : (i,o)

suma_alternanta([], 0).  % Cazul de bază: lista este goală, suma este 0.

suma_alternanta(L, Sum) :-
    calcul(L, 0, 1, Sum).


calcul([], CurrentSum, _, CurrentSum).  % Cazul de bază: lista este goală.

calcul([H|T], CurrentSum, Index, FinalSum) :-
    (   Index mod 2 =:= 0  % Index par
    ->  NewSum is CurrentSum - H
    ;   NewSum is CurrentSum + H  % Index impar
    ),
    NewIndex is Index + 1,
    calcul(T, NewSum, NewIndex, FinalSum).
% Model
% Suma_alternata_h(x1 x2...xn,suma,index){
%   suma_alternata_h(x2....xn,suma+x1,index+1), index % 2 ==1
%   suma_alternata_h(x2...xn,suma-x1,index+1), index % 2 == 0
%   suma, n == 0
%}
% Suma_alternata(x1 x2 ... xn){
%   Suma_alternata_h(x1 x2 ... xn, 0, 1)
%   0 , n==0
%
%}
%suma_alternata([10,5,4,9],Sum) (0)
%suma_alternata([10,20],Sum) (-10)
%suma_alternata([10,9,1,2],Sum) (0)
