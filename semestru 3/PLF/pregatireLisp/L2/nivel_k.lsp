
;PROBLEMA 2

(defun nivel_k (l k)
    (cond
        ((null l) NIL)
        ((= k 1) (list (car l)))
        (t (append (nivel_k (cadr l) (- k 1)) (nivel_k (caddr l) (- k 1)) ) )
    )
)
(nivel_k '(A (B) (C (D) (E (F) (G (H) (I))))) 2)
