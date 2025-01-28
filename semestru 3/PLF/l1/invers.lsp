; b) Definiti o functie care inverseaza o lista impreuna 
; cu toate sublistele sale de pe orice nivel.

; inversare( l1 l2 ... ln){
;   [], n == 0
;   inversare(l2 ... ln) U inversare(l1), l1 este lista
;   inversare(l2 .... ln) U l1, l1 este atom
;   
;}
(DEFUN inversare (l)
    (COND
        ((null l) nil) ; daca lista este goala returnam nil
        ((listp (CAR l)) (APPEND (inversare (CDR l)) (list (inversare (CAR l))))) ; daca l este lista
        ; mergem mai departe in lista si reunim cu mersul in adancime pe sublista
        (T (APPEND (inversare (CDR l)) (list (CAR l)))); altfel mergem mai departe si reunim cu elementul l
    )
)

(print (inversare '(1 2 5 (3 4 (A 8) (B)))))
(print (inversare '(1 2 3 (4 5 6) (7 8 9) 10)))

