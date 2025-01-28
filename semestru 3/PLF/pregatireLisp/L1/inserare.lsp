;;1. 
;a)  Sa se insereze intr-o lista liniara un atom a dat dupa al 2-lea, al 4-lea, 
;al 6-lea,....element. 



; inserare(l1...ln,e,poz){
;   l1 U e U inserare(l2...ln,e,poz+1) , poz % 2 == 0
;   l1 U inserare(l2...ln,e,poz+1)
;}

(defun inserare (l e poz)
  (cond
    ((null l) nil)  ; Base case: if the list is empty, return nil.
    ((= (mod poz 2) 0)
     (append (list (car l) e) (inserare (cdr l) e (+ poz 1))))
    (t
     (cons (car l) (inserare (cdr l) e (+ poz 1))))))
           
;(print (inserare '(1 3 5 7) 3 1))




