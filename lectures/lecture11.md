---
layout: default
title: "Lecture 11: Recursion and iteration in Clojure"
---

Like most functional programming languages, Clojure emphasizes the use of recursion for iterated computation.

Example, merging two sorted lists:

{% highlight clojure %}
(defn my-merge [left right]
  (cond
    (empty? left) right
    (empty? right) left
    (< (first left) (first right)) (cons (first left) (my-merge (rest left) right))
    :else (cons (first right) (my-merge left (rest right)))))
{% endhighlight %}

Example run:

{% highlight clojure %}
user=> (def list1 '(1 5 9 33 50 97))
#'user/list1
user=> (def list2 '(3 6 28 44 67 68 99))
#'user/list2
user=> (my-merge list1 list2)
(1 3 5 6 9 28 33 44 50 67 68 97 99)
{% endhighlight %}

Issue: each function call in Clojure creates a new stack frame.  So, a deep recursion could exhaust the stack (leading to a `StackOverflowError`).

Solution: use the `recur` form for recursive calls in tail position.  A call is in tail position if the result of the call is returned as the result of the overall function.  Note that `my-merge` does not have its recursive calls in tail position, because a call to `cons` is required after each recursive call to `my-merge` returns.  By adding a helper function with an  *accumulator parameter*, we rewrite the function using `recur`:

{% highlight clojure %}
(defn my-merge-work [left right accum]
  (cond
    (and (empty? left) (empty? right)) (reverse accum)
    (empty? left) (recur left (rest right) (cons (first right) accum))
    (empty? right) (recur right (rest left) (cons (first left) accum))
    (< (first left) (first right)) (recur (rest left) right (cons (first left) accum))
    :else (recur left (rest right) (cons (first right) accum))))

(defn my-merge [left right]
  (my-merge-work left right '()))
{% endhighlight %}

Note: because the `accum` parameter holds the partial result of the computation, the cases where one of the lists (`left` or `right`) has become empty are slightly more complicated.

**Question**: Why is it necessary to reverse the accumulator in the case when both the left and right lists are empty?

Clojure defines the `loop` form as a compact alternative to creating an explicit helper function.

{% highlight clojure %}
(defn my-merge [left right]
  (loop [ll left
         rr right
         accum '()]
    (cond
      (and (empty? ll) (empty? rr)) (reverse accum)
      (empty? ll) (recur ll (rest rr) (cons (first rr) accum))
      (empty? rr) (recur rr (rest ll) (cons (first ll) accum))
      (< (first ll) (first rr)) (recur (rest ll) rr (cons (first ll) accum))
      :else (recur ll (rest rr) (cons (first rr) accum)))))
{% endhighlight %}
