;munte(l1...ln){
;	null, n == 0
;	creste(l1...ln), altfel
;
;
;creste(l1...ln){
;	creste(l2...ln),l1 < l2
;	scade(l2...ln),altfel
;}
;scade(l1...ln){
;	true,n == 0
;	scade(l2...ln),l1>l2
;	false, altfel


(defun creste (l)
  (cond
    ((< (car l) (car (cdr l))) (creste (cdr l)))
    (t (scade (cdr l)))
))

(defun scade (l)
  (cond
    ((null l) t)
    ((null (car (cdr l))) t)
    ((>(car l) (car (cdr l))) (scade (cdr l)))
    (t nil)
))

(defun munte (l)
	(cond
	     ((null l) nil)
	     (t (creste l))
))

(print (munte `(1 2 3 4 5 4 3 2 1)))
(print (munte `(1 2 3 4 5 4 3 2 3)))
(print (munte `(1 2 3 4 5 4 5 2 1)))
(print (munte `(3 2 3 4 5 4 3 2 1)))

