;Sa se construiasca o functie care intoarce produsul atomilor numerici pari 
;dintr-o lista, de la orice nivel.

; produs(l1...ln, prod){
;	produs(l2...ln,prod*l1),l1 != lista
;	produs(l2...ln, produs(l1,prod)), l1 == lista
;	1, n== 0


(defun produs (l prod)
  (cond 
    ((null l) prod)
    ((listp (car l)) (produs (cdr l) (produs (car l) prod)))
    (t (produs (cdr l) (* prod (car l))))
))

(print(produs`(1 2 3 4 5 6 7 8 9 10) 1))
(print(produs`(1 2 (3 4 5) 6 (7 (8 9 (10)))) 1))
