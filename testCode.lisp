(def x 3)
(def y 4)
(def z? (println "Hello, Lisp2!"))
(println "Hello, Lisp!")
(fn p [str] (println str))
(p (+ 3 4))
(fn fact [n]
  (if (> n 0)
     (* n (fact (- n 1)))
     1))
(fn fact-tailrec [acc n]
  (if (> n 0) (fact-tailrec (* acc n) (- n 1))
   acc))

(p (fact 3))
(p (fact-tailrec 1 3))

(fn fib [n]
  (if (<= n 1)
    n
    (+ (fib (- n 1)) (fib (- n 2)))))

(p (fib 6))
(z?)
(println 3/5)
(println (float 3/5))
(def name (read-line "Input your name: "))
(println (++ (++ "Hello, " name) "!"))
