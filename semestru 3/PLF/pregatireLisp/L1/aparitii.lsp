;d)  Sa se scrie o functie care determina numarul de aparitii ale unui atom dat 
;intr-o lista neliniara.


; aparitii(l1...ln, elem){
;   0, n == 0;
;   1+ aparitii(l2... ln,elem), l1 == elem)
;   aparitii(l1,elem)+aparitii(l2...elem), l1 == lista,
;   aparitii(l2...elem), altfel
;
;}

(defun aparitii (l e)
    (cond 
        ((null l ) 0)
        ((and (atom (car l)) (= (car l) e) ) (+ 1 (aparitii(cdr l) e)))
        ((listp (car l)) (+ (aparitii (car l) e) (aparitii (cdr l) e)))
        (t (aparitii (cdr l) e))
    )
)

(print (aparitii `(1 2 1 2 3 5 (1 3 (1 2))) 1))