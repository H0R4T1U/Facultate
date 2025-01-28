;c) Dandu-se o lista, sa se construiasca lista primelor 
; elemente ale tuturor elementelor lista ce au un numar 
;impar de elemente la nivel superficial. 
;Exemplu: (1 2 (3 (4 5) (6 7)) 8 (9 10 11)) => (1 3 9).





; verificare_primul_element(l){
; [], l este atom
; l1 U urmator(l2...ln), l este lista de lungime impara
; urmator(l2...ln), l este lista de lungime para
;}

;urmator(l1 l2 ... ln){
; [], n == 0
; verificare_primul_element(l1) U urmator (l2 ... ln)
;}
(defun odd_list_length (l)
    (= (mod (length l) 2) 1) ; verificare daca lungimea listei este impara
)

(defun verificare_primul_element (l)
  (cond
    ((atom l) nil)  ; Dacă `l` este un atom, returnăm nil.
    ((odd_list_length l)
     (cons (car l) (urmator (cdr l))))  ; Dacă lista are lungime impară, păstrăm primul element.
     ; si o parcurgem mai departe
    (t (urmator (cdr l))); altfel doar o parcurgem mai departe
  )
)  

(defun urmator (lst)
; ruleaza verificare_primul_element pentru fiecare element din lista
  (cond 
    ((null lst) nil)  ; Dacă lista este goală, returnăm nil.
    (T (append (verificare_primul_element (car lst)) (urmator (cdr lst)))); reunim rezultatul vpe de primul element din lst
    ; cu restul rezultatelor
  )
    )  ; Combinăm rezultatele recursiv.

(print (verificare_primul_element '(1 2 (3 (4 5) (6 7)) 8 (9 10 11)))) ; 1 3 9
(print (verificare_primul_element '(7 (9 (7 3 6 ) (8 2 7 1 0)) 3  8 (9 10 11)))) ; 7 9 7 8 9
(print (verificare_primul_element '(A (B (C D E ) (F G H I J)) K  L (M N O)))) ; A B C F M