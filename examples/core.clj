(ns clojure-quiz.core)

; Define a clojure function that will add a specified amount to
; every number in a sequence, returning the transformed sequence
; as a result.
;
; Example invocations:
;   (add-to-all [11 13 19 1 16] 4)
;     => [15 17 23 5 20]
;   (add-to-all [17 3 16 11 7] -3)
;     => [14 0 13 8 4]
;   (add-to-all [] 20)
;     => []
;
; Note that the example invocations show a vector as a result,
; but a list would also be fine.
;
; Hint: use the built-in map function
;
(defn add-to-all [a-seq value]
  (map (fn [x] (+ x value)) a-seq))

; Define a clojure function called count-evens that returns a
; count of how many even integers are contained in the sequence
; passed as the parameter.  You can assume that all of the
; elements in the sequence will be integers.
;
; Example invocations:
;   (count-evens [6 4 19 2 16 10 12 14 17 13])
;     => 7
;   (count-evens [4 0 18 15 13 1 8 7 9 19])
;     => 4
;   (count-evens [])
;     => 0
;
(defn count-evens [a-seq]
  (loop [values a-seq
         accum 0]
    (cond
      (empty? values) accum
      (= (mod (first values) 2) 0) (recur (rest values) (+ accum 1))
      :else (recur (rest values) accum))))

; Define a clojure function called find-min that returns the minimum
; value of the sequence passed as the parameter.
; You may assume that the sequence will have at least one
; value in it.
;
; Example invocation:
;   (find-min '(12 88 5 20 6 67 99 4 17))
;     => 4
;
(defn find-min [a-seq]
  (loop [min (first a-seq)
         values (rest a-seq)]
    (cond
      (empty? values) min
      (< (first values) min) (recur (first values) (rest values))
      :else (recur min (rest values)))))

; Define a clojure function called pairs.  It takes a sequence as
; a parameter, and returns a sequence of two-element sequences,
; where each two-element sequence is an adjacent pair of values
; from the original sequence.  The pairs should be returned in order.
; You may assume the original sequence will have at least two
; members.
;
; Example invocation:
;   (pairs '(:a :b :c :d))
;     => ((:a :b) (:b :c) (:c :d))
;
; Note that it does not matter what kind of sequence you return
; as the overall result, or what kind of sequence you use for
; the pairs.  (I.e., either lists or vectors are fine.)
;
(defn pairs [a-seq]
  (loop [elt (first a-seq)
         members (rest a-seq)
         accum '()]
    (cond
      (empty? members) (reverse accum)
      :else (recur (first members) (rest members) (cons (list elt (first members)) accum)))))
