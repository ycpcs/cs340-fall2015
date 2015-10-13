---
layout: default
title: "Lab 12: Clojure review"
---

# Getting started

Download [clojure-review.zip](clojure-review.zip).  It is an Eclipse project, so you can import it into Eclipse using **File&rarr;Import...&rarr;General&rarr;Existing projects into workspace&rarr;Archive file**.  You should see a project called **clojure-review** in your Eclipse workspace.

# Your task

Complete the **make-pair**, **double-apply**, **double-applicator**, **my-flatten**, and **conj-all** functions in `src/clojure_review/core.clj`.  Each function is described by a detailed comment with example inputs and expected results.

You can test your functions by running the command `lein test` in a terminal window from the root of the project.

You can also start a Clojure REPL (in Eclipse) by right-clicking in `core.clj` and choosing **Clojure&rarr;Load file in REPL**.  This is very useful for testing your functions interactively.

<div class="callout"><b>Important</b>: make sure you follow the requirements for each function.  For example, <b>my-flatten</b> must be recursive, and <b>conj-all</b> must be tail recursive.  Trivial solutions (e.g., <code>(defn my-flatten [a-seq] (flatten a-seq))</code> are not acceptable.</div>

## Resources you may use

You may use your textbook, the [course website](http://ycpcs.github.io/cs340-fall2014), the [Clojure MOOC](http://mooc.cs.helsinki.fi/clojure) website, the [clojure.org](http://clojure.org/) website, and the [clojuredocs.org](http://clojuredocs.org/) website.

Note that this lab is not graded, but it is intended to be a preview of Exam 2, so you should try to complete it using only the resources listed above (which you will also have access to for Exam 2.)

# Solutions

Here are my solutions: [lab12.clj](lab12.clj).
