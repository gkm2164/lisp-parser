(module MapTest)

;;; Object is defined with brace.
;;; Each key is mapped to value
;;; Key is special type
(def obj {
  :id 1
  :name "Gyeongmin Go"
  :age 33
})

;;; You can confirm that these 2 are identical
(assert "should be same" (= :id (key "id")))

;;; The key is a function that takes a parameter.
;;; Below 2 are identical.
(assert "Two are identical" (= (:id obj) ((key "id") obj)))

;;; Map object is immutable. You can't make a change on map.
(println obj)
(def new-obj (+ obj (entry :new-id "new value")))
(println new-obj)

 ;;; This should print null.
(println (:new-id obj))

 ;;; This one has value.
(println (:new-id new-obj))

(println (:id obj))
(println (:name obj))
(println (:age obj))

(println (:new-entry (+ obj (entry :new-entry "new value"))))

(println (keys obj))

(println ((key "id") obj))