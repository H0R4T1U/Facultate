;functie(l1...ln,e,niv):-
;         e, l1 este atom si niv % 2 ==1
;         l1, l1 atom si niv % 2 == 0
;         functie(l1) U functie(l2) U .... functie(ln)
;functie(L:List,E:Integer,Niv:Integer)
;L:arborele
;E:elementul
;Niv:nivelul curent
(defun functie (l e n)
    (cond
        ((and (atom l) (= 1 (mod n 2))) (list e))
        ((atom l) (list l))
        (t (mapcar (lambda (x) (functie x e (+ n 1))) l))
    )
)
(print (functie `( a (b (g)) (c (d (e)) (f))) `h -1)))