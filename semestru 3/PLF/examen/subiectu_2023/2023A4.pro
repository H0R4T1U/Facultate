%i o
f([], 0).
f([H|T],S):-f(T,S1),S1 is S-H.
%  Eroare deoarece S nu poate fi evaluat
% 