; cmmmc_list(l1...ln):
;	nil, n==0
;	l1, n== 1
;	cmmmc(cmmmc(l1,l2),cmmmc_list(l2...ln)), daca l1 si l2 sunt atomi
;	cmmmc(cmmmc_list(l1),cmmmc_list(l2...ln)), daca l1 este lista 
;	cmmmc(l1,cmmmc_list(l2....ln), daca l2 este lista	
;
;
;
;
;cmmdc(a,b){
;	a, daca a=b
;	cmmdc(a-b,b), daca a > b
;	cmmdc(a,b-a), daca b>a;
;cmmmc(a,b){
;	1, daca a si b nu sunt numere
;	a, daca b nu este numar
;	b, daca a nu este numar
;	a*b/cmmdc(a,b), daca a si b sunt numere
;

; cmmdc(a:integer,b:integer) => cmmdc(a,b):integer
(defun cmmdc (a b)
  (cond
    ((= a b) a)
    ((> a b) (cmmdc (- a b) b))
    ((< a b) (cmmdc a (- b a)))
  )
)
; cmmmc(a: atom, b:atom) => cmmc(a,b):integer
(defun cmmmc (a b)
  (cond
    ((and (not (numberp a)) (not (numberp b))) 1) ; daca a si b nu sunt numere returnam 1
    ((not (numberp b)) a) ; daca b nu este numar returnam a
    ((not (numberp a)) b) ; daca a nu este numar returnam b
    (t (/ (* a b) (cmmdc a b)))
  )
)

; cmmc_list(l:List)
(defun cmmmc_list (l)
  (cond 
    ((null l) nil) ; l = null
    ((null (cdr l)) (car l)) ; n ==1 => l1
    ((and (atom(car l)) (atom(cadr l))) (cmmmc ( cmmmc (car l) (cadr l)) (cmmmc_list(cdr l)))) ; daca l1 si l2 sunt atomi    
    ((listp (car l)) (cmmmc (cmmmc_list(car l)) (cmmmc_list (cdr l)))) ; daca l1 este lista
    ((listp (cadr l)) (cmmmc (car l) (cmmmc_list(cdr l)))) ; daca l2 este lista
  )
)
(print (cmmmc_list `(12 2 4 6 8)))
(print (cmmmc_list `(4 (10) (5 6)2)))
(print (cmmmc_list `(1 A (2 3 B 6) 5)))
(print (cmmmc_list `(1 (2 (3 (4 (F)5)6)1)1)))
