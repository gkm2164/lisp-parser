(println "=== Declare variables ===")
(def v 3)
(def str "Something Wonderful!")
(def name "YoonJung")

(println "=== Testing variable reference ===")
(println v)
(println str)
(println name)
(println "=== Print char ===")
(println #\n)
(println "=== Print String ===")
(println "Hello World")
(println "=== Add 2 values ===")
(println (+ 2 3))
(println "=== Add 2 values, Int, Double ===")
(println (+ 2 5.2))
(println "=== 2 * (3 - 2) / 5.3 == Should be double ===")
(println (* 2 (/ (- 3 2) 5.3)))
(println "=== Add number to variable ===")
(println (+ v 10))

(println "=== Do some string concatenation ===")
(println (+ (+ "Hello, " name) "!"))
(println (+ name " is beautiful woman!"))
(println (+ name 123))

(println "=== Sequence Test! ===")
(def single-element [1])
(def seq [1 2 3 4 5 6 7 8 9 10])
(def seq-multi-types [1 2 3 name])
(def seq-nested [1 2 3 4 5 [1 2 3 4 5]])


(println "=== Single element ===")
(println single-element)
(println seq)
(println seq-nested)
(println seq-multi-types)

(fn print-seq (x y)
  (println [x y]))

(fn other-seq (x)
  (println x))

(fn concat (x y) [x y])

(fn concat-clojure (x y) [x y v])

(print-seq "Hello" "World")
(println "Hello World!")
(print-seq "something" "World")
(println (concat 3 5))
(other-seq "something2")
(print-seq "something" "World")
(other-seq "something3")

(println (concat-clojure (3 5)))
(println (take 3 [1 2 3 4 5]))
(println (drop 3 [1 2 3 4 5]))

(println (+ [] 3))
(println (+ [] "ABCDEF"))

(println (flatten seq-nested))

(println "Test Unit type")
(println (println "this would be printed first"))

(println "Doing some Type casting")
(println (char (int #\c)))
(println (int 1.3))
(println (double 1))
(println (str 1234))