% model de flux : (i)
este_vale([_, _]) :- false.

este_vale([X,Y|Rest]) :-
    X > Y,             
    scade([Y|Rest]).   

scade([X, Y|Rest]) :-
    X > Y,             
    scade([Y|Rest]).   
scade([X, Y|Rest]) :-
    X < Y,             
    creste([Y|Rest]). 

creste([X, Y|Rest]) :-
    X < Y,             
    creste([Y|Rest]).
creste([_]).
creste([]).      

% MODEL MATEMATIC
% este_vale(x1 x2 ...xn){
%           false, n == 2
%           scade(x1 x2 ...xn) daca x1 > x2
%           false altfel
%}
% descendent(x1,x2,...xn){
%               descendent(x2,...xm),x1 >= x2
%               ascendent(x2,....xn), altfel
%               }
% ascendent(x1,x2,...xn){
%   ascendent(x2,...xn), x1 < x2
%   fals, x1 > x2
%   true, []
%}
% vale([10,8,6,8,10]) true
% vale([10,6,8,10]) true
% vale([1,2,3,4,5]) false
% vale([10,8,10,8]) false
% vale([5,4,3,2,1]) false