(DEFUN cmmdcList (l)
    (COND
        ((null l) nil)
        ((numberp (CADR l)) (cmmdc (CAR l) (CADR l)))
        (T (CONS (cmmdc (CAR l) (CADR l)) (cmmdcList (CDR l))))
    )
)

(DEFUN cmmdc (a b)
    (COND
        ((equal b 0) a)
        (T (cmmdc b (mod a b)))
    )
)

(DEFUN flattenList (l)
    (COND
        ((null l) nil)
        ((listp (CAR l)) (APPEND (flattenList (CAR l)) (flattenList (CDR l))))
        (T (CONS (CAR l) (flattenList (CDR l))))
    )
)

(DEFUN main (l)
    (cmmdcList (flattenList l))
)
