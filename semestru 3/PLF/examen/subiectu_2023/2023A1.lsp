(DEFUN Fct(F L)
    (cond 
        ((NULL L) nil)
        ((FUNCALL F (CAR L)) (CONS (FUNCALL F (CAR L))(FCT F (CDR L))))
        (T nil)
    )
)
(defun FCT(F L)
    ((lambda (x)
        (cond
            ((NULL l) nil)
            (X (cons X (Fct F (cdr L))))
            (T nil)
        )
    ) (funcall F (car l)))
)

