; d) Sa se construiasca o functie care intoarce suma atomilor 
; numerici dintr-o lista, de la nivelul superficial.

; suma(l1 l2 ... ln){
;   0 , n==0;
;   l1 + (suma(l2 ... ln)), l1 este nr
;   suma(l2 ... ln), altfel
;}
(defun suma (l)
    (cond
        ((null l) 0) ; daca lista e goala returnam 0
        ((numberp (car l)) (+ (car l) (suma (cdr l)))) ; daca primul element din lista este nr
        ((listp (car l)) (+ (suma (car l)) (suma (cdr l))))
        ; il aduagam la suma si mergem mai departe
        (T (suma (cdr l))) ; altfel mergem mai departe
    )
)

(print (suma '(1 2 5 1 (3 4 (A 8 5) (B)))))
(print (suma '((3 4 (A 8 5) (B)) 5 3 2)))
(print (suma '(A B C D (E F (A G  H) (B)))))
(print (suma '()))