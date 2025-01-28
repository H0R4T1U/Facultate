; Model Matematic Recursiv ------------------------------------------------------------------------------------
; Rezolva(x,l1 l2,l3 l4 ... ln){
;       cauta(x,l1,0,l2,l3 ... ln)
;}
; Cauta(x, nod,nivel,nr_copii,l1 l2 ... ln){
; nivel, daca x == nod
; nil, daca n == 0 , n = lungime l
; l, daca nr_copii = 0
; cauta(x, nod nivel nr_copii-1,ls ), daca  ls = cauta(x, l1, nivel+1,l2,l3 ... ln) este lista
; cauta(x,l1,nivel+1,l2,l3...ln) , altfel
;

; Problema 9 NExt

;-----------------------------------------------------------------------------------------------------------------------------------------------------------------

(defun cauta (x nod nivel nr_copii l)
  (cond
    ((eq x nod) nivel) ; Daca nod-ul este egal cu nodul cautat returnam nivelul
    ((null l) nil) ; daca lista s a terminat si nu am gasit nimic returnam nil
    ((= nr_copii 0) l) ; Daca nodul nu mai are copii returnam restul listei
    (t 
     (cond
       ((listp (cauta x (car l) (+ nivel 1) (cadr l) (cddr l))) ; apelam recursiv pentru primul element din lita si verificam daca a fost gasit X sau nu
        (cauta x nod nivel (- nr_copii 1) (cauta x (car l) (+ nivel 1) (cadr l) (cddr l)))) ;  continuăm explorarea pentru ceilalți copii ai nodului curent.
       (t
        (cauta x (car l) (+ nivel 1) (cadr l) (cddr l))))))) ; altfel intoarcem rezultatu


(defun rezolva (x l)
    (cauta x (car l) 0 (cadr l) (cddr l))  
)


;(print (rezolva 'A(list 'A 2 'B 2 'C 2 'E 0 'F 1 'G 0 'D 1 'H 2 'I 0 'J 2 'K 0 'L 0 'M 2 'N 0 'O 2 'P 0 'Q 2 'R 0 'S 1 'T 0)))
;(print (rezolva 'B(list 'A 2 'B 2 'C 2 'E 0 'F 1 'G 0 'D 1 'H 2 'I 0 'J 2 'K 0 'L 0 'M 2 'N 0 'O 2 'P 0 'Q 2 'R 0 'S 1 'T 0)))
;(print (rezolva 'N(list 'A 2 'B 2 'C 2 'E 0 'F 1 'G 0 'D 1 'H 2 'I 0 'J 2 'K 0 'L 0 'M 2 'N 0 'O 2 'P 0 'Q 2 'R 0 'S 1 'T 0)))
;(print (rezolva 'F(list 'A 2 'B 2 'C 2 'E 0 'F 1 'G 0 'D 1 'H 2 'I 0 'J 2 'K 0 'L 0 'M 2 'N 0 'O 2 'P 0 'Q 2 'R 0 'S 1 'T 0)))
;(print (rezolva 'R(list 'A 2 'B 2 'C 2 'E 0 'F 1 'G 0 'D 1 'H 2 'I 0 'J 2 'K 0 'L 0 'M 2 'N 0 'O 2 'P 0 'Q 2 'R 0 'S 1 'T 0)))
;(print (rezolva 'T(list 'A 2 'B 2 'C 2 'E 0 'F 1 'G 0 'D 1 'H 2 'I 0 'J 2 'K 0 'L 0 'M 2 'N 0 'O 2 'P 0 'Q 2 'R 0 'S 1 'T 0)))
(print (rezolva 'N(list 'A 2 'B 2 'D 1 'G 0 'E 2 'H 1 'L 2 'M 0 'N 0 'I 0 'C 1 'F 2 'J 0 'K 1 'Q 1 'P 2 'R 0 'S 0)))