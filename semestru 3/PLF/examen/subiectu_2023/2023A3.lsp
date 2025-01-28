(defun G(L)
(List (car L) (car L)))

(SETQ Q `G)
(SETQ P `Q)
(print (funcall P `(A B C)))

// se va obtine A A deoarece Q va evalua G iar P va evalua q care este tot G 