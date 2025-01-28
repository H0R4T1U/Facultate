(DEFUN cmmmc (a b)
  "Calculates the least common multiple of two numbers."
  (COND
    ((equal a 0) 0)  ; If either number is 0, LCM is 0.
    ((equal b 0) 0)
    (T (/ (* a b) (cmmdc a b))))) ; LCM = (a * b) / GCD

(DEFUN cmmdc (a b)
  "Calculates the greatest common divisor using Euclid's algorithm."
  (COND
    ((equal b 0) a)
    (T (cmmdc b (mod a b)))))

(DEFUN cmmmcList (l)
  "Computes the least common multiple of numbers in a linear list."
  (COND
    ((null l) nil)
    ((null (CDR l)) (CAR l))  ; Single element, return it.
    (T (cmmmc (CAR l) (cmmmcList (CDR l)))))) ; Recursively calculate LCM.

(DEFUN flattenList (l)
  "Flattens a nested list into a linear list."
  (COND
    ((null l) nil)
    ((listp (CAR l)) (APPEND (flattenList (CAR l)) (flattenList (CDR l))))
    (T (CONS (CAR l) (flattenList (CDR l))))))

(DEFUN main (l)
  "Computes the least common multiple of numbers in a non-linear list."
  (cmmmcList (flattenList l)))

;; Example usage:
(print (main '((4 6) (8 12))))
