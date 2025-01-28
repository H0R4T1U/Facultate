;9.  Definiti o functie care substituie un element E prin elementele 
;     unei liste L1 la toate nivelurile unei liste date L. 

; b)
; substituire(l1..ln, e, K) =
; 	K , n > 0 si l1 = atom si l1 = e
; 	l1  n > 0 si l1 = atom si l1 /= e
;	  substituire(l1, e, K) U substituire(l2, e, K)U...substituire(ln,e,K), altfel
;
; Teste:
;		substituire([1 [1 2 [1 2 3]]], 1, [5 5]) -> [[5 5] [[5 5] 2 [[5 5] 2 3]]]
;substituire(l:list, e:atom,k:list)
(defun substituire (l e k)
  (mapcar
   (lambda (x)
     (cond
       ;; Dacă elementul este atom și egal cu e, înlocuiește-l cu k
       ((and (atom x) (equal x e)) k)

       ;; Dacă elementul este o listă, aplică funcția recursiv
       ((listp x) (substituire x e k))

       ;; În alte cazuri, returnează elementul nemodificat
       (t x)))
   l))
(print(substituire`(1 (1 2 (1 2 3)))  1 `(5 5)))