;c)  Sa  se  elimine  toate  aparitiile  elementului  numeric  maxim    dintr-o  lista 
;neliniara.

; maxim(l1...ln  max){
;	max  n ==0
;	maxim(l2...ln maxim(l1 max))  l1 = lista
;	maxim(l2...ln l1)  max < l1
;	maxim(l2...ln max)  altfel
;}
;elimina(l1...ln elem){
;	elimina(l1  elem) U elimina(l2...ln elem)  l1 = lista
;	elimina(l2...ln. elem)  elem = l1
;	l1 U elimina(l2...ln elem)  altfel
;	null, n ==0

(defun maxim (l max)
  (cond
    ((null l) max)
    ((listp (car l)) (maxim (cdr l) (maxim (car l) max)))
    ((< max (car l)) (maxim (cdr l) (car l)))
    (t (maxim (cdr l) max))))


(defun elimina (l elem)
	(cond
	  ((null l) nil)
	  ((listp (car l)) (cons (elimina(car l) elem) (elimina(cdr l) elem)))
	  ((=(car l) elem) (elimina(cdr l) elem))
	  (t (cons (car l) (elimina(cdr l) elem)))
))

(defun main(l)
	(elimina l (maxim l -1))
)

(print (main `(1 2 3 4 5 10 10 10 (3 4 5 10) (4 3 (10 4 10)))))
