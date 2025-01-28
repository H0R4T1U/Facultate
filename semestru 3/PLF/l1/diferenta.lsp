
; a) Sa se scrie o functie care intoarce diferenta a doua multimi.

; apartine(element, l1 l2 ln){
;   null, n == 0
;   true, element == l1
;   apartine(element, l2 ... ln)
;}
; diferenta(l1 l2 ... ln ,k1 k2 ... kn){
;   [], ln = 0
;   l1 U diferenta(l2 ... ln, k1 k2 ... kn),! apartine(l1,k1 k2 ... kn)
;   diferenta(l2 ... ln, k1 k2 ... kn ), altfel: apartine(l1, k1 k2 ... kn)
;
;}
(defun diferenta (a b)
    (cond
    ((null a ) nil ) ; a este null => nil
        ;; Caz recursiv: elementul curent din A nu este în B, îl păstrăm
        ((not (apartine (car a) b))
            (cons (car a) (diferenta (cdr a) b)))
        ;; Caz recursiv: elementul curent din A este în B, îl excludem
        (t (diferenta (cdr a) b))
    )
)
    
(defun apartine (element lst)
  ;verificam daca elementul este in lista
  (cond
    ;; Caz de bază: lista este goală, element nu a fost găsit
    ((null lst) nil)
    ;; element este gasit ca element in lista
    ((equal element (car lst)) t)
    ;; Continuăm să căutăm în restul listei
    (t (apartine element (cdr lst)))))

(print (diferenta '(1 3 5 7) '(3 5 8))) ; (1,7)
(print (diferenta '(a b c d 5 7) '(a d 3 5 8))) ; (B C 7)
