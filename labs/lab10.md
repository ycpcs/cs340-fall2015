---
layout: default
title: "Lab 10: Sieve of Eratosthenes"
---

# Getting started

Start a new Clojure project in Eclipse using **New &rarr; Project &rarr; Clojure &rarr; Clojure Project**.  Name the new project **lab10**.

Open the file **src/lab10/core.clj**.  Create your functions in this file.  When you are ready to test your functions, start a REPL by right-clicking in the editor and choosing **Clojure &rarr; Load file in REPL**.  When you modify your code, do the same thing to reload your definitions in the REPL.

If you would prefer to use Light Table, or edit your code in a text editor and run the Clojure REPL from the command line, feel free.

# Your task

Complete the exercises shown below.

Start with the following function definition (from [Lecture 13](../lectures/lecture13.html)):

{% highlight clojure %}
(defn make-is-multiple-of [n]
  (fn [x]
    (= (mod x n) 0)))
{% endhighlight %}

## apply-sieve

Write a function called `apply-sieve`.  It takes a sequence of integers whose first member is a prime number.  It should return a sequence in which every multiple of that prime number (including itself) is removed.

Example call:

    => (apply-sieve [2 3 4 5 6 7 8 9 10 11 12 13 14 15])
    (3 5 7 9 11 13 15)

Hints/requirements:

* Use the `complement` and `make-is-multiple-of` functions to create a predicate that returns true when a number is *not* a multiple of a given integer: e.g., `(complement (make-is-multiple-of 3))` would return a predicate function that is true for all integers that *aren't* multiples of 3
* You could also use the [remove](https://clojuredocs.org/clojure.core/remove) function rather than `filter` and `complement` to remove all of the multiples of the first element
* Use `filter` with your predicate function to remove all of the multiples of whatever the first element of the input sequnce is

## sieve

Write a function called `sieve` that computes a sequence containing all of the prime numbers up to a specified maximum integer.  Example call:

    => (sieve 40)
    (2 3 5 7 11 13 17 19 23 29 31 37)

The basic idea is that you can use the `apply-sieve` function repeatedly to discover all of the prime numbers: if applied repeatedly starting with a list whose first element is a prime number, it is guaranteed to return a list whose first element is the next prime number.  You can continue applying it until the first element is greater than or equal to the square root of the maximum integer.

This algorithm is called the [Sieve of Eratosthenes](https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes).

Hints/specifications:

* Use `(range 2 max)` to generate a sequence of integers from 2 to `max` inclusive (2 is the first prime number)
* Use `loop` to perform a tail recursive computation: the loop variables should be a sequence of integers starting with a prime number and an accumulator storing the prime numbers that have been discovered so far (initially empty)
* Use `(Math/sqrt n)` to find the square root of `n`
* You can use the `concat` function to concatenate two sequences

I feel like there should be a clever way to do this computation using `reduce` (rather than doing an explicit tail recursion using `loop`), although I'm not sure.
