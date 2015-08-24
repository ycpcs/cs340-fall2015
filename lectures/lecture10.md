---
layout: default
title: "Lecture 10: Why Clojure?"
---

[Clojure](http://clojure.org/) is a functional language that runs on the Java Virtual Machine.  It is a dialect of LISP.  It was designed and implemented by [Rich Hickey](https://twitter.com/richhickey).

Why learn Clojure?

* Functional programming: no side-effects, no assignments, no mutable data
* Concise syntax
* Powerful functional data structures: lists, vectors, sets, maps
* Anonymous functions (also called *closures*)
* Higher-order functions (functions can return functions)
* Powerful tools for *composing* functions: map, reduce, filter
* Interoperability with Java (any Java class/method can be used from Clojure code)
* Macros (code that generates or transforms code: a program add new syntactic forms to the language)
* Interactive, incremental development (the REPL, read-eval-print loop)

Recommended reading: [Beating the Averages](http://www.paulgraham.com/avg.html) by [Paul Graham](http://www.paulgraham.com/): talks about Common LISP, but everything he says about LISP is true of Clojure.

Data types:

* Integers, floating point numbers
* Keywords (**:a**, **:hello**): often used as opaque identifiers
* Strings (which are actually Java String objects)
* Lists (**'(1 2 3)**, **'(:a :b :c)**)
* Vectors (**[1 2 3]**, **[:a :b :c]**)
* Sequences (an abstract generalization of lists, vectors, and any other data structure that represents a sequence of values: many built-in functions operate on sequences such as **conj**, **first**, **rest**)
* Sets (**#{:a :b :c}**)
* Maps (**{:a 1, :b 2, :c 3}**)
