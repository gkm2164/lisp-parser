(def T true)
(def F false)
(def bool-list [T F])

(def lt (< 3 5))
(def gt (> 3 5))
(def a (and T T))
(def o (or T T))
(def e (= T T))
(def ne (/= T F))

(println T)
(println F)
(println bool-list)
(println lt)
(println gt)
(println a)
(println o)
(println e)
(println ne)

(def n1 3)
(def n2 5)

(if (< n1 n2)
 (println "n2 is bigger than n1")
 (println "n1 is bigger than n2"))

(if (> n1 n2)
  (println "n1 is bigger than n2")
  (println "n2 is bigger than n1"))

(println (flatten
    (loop for x in [1 2 3 4 5]
          for y in [1 2 3 4 5]
          (+ x y))))