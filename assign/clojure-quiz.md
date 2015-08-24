---
layout: default
title: "Clojure Quiz"
---

# Getting started

Download [clojure-quiz.zip](clojure-quiz.zip).

You can import it into Eclipse using **File &rarr; Import... &rarr; General &rarr; Existing projects into workspace &rarr; Archive File**, if you would like to use Counterclockwise.

Or, you can just unzip it and use whatever editor you normally use for Clojure.

You will be editing the file `src/clojure_quiz/core.clj`.

<div class="callout">I am not necessarily expecting you to complete the entire quiz. Just do your best.  Try to get <b>at least</b> one function working.</div>

## Resources you may use

You may use your textbook, the [course website](http://ycpcs.github.io/cs340-fall2014), the [Clojure MOOC](http://mooc.cs.helsinki.fi/clojure) website, the [clojure.org](http://clojure.org/) website, and the [clojuredocs.org](http://clojuredocs.org/) website.

# Your task

Complete the **add-to-all**, **count-evens**, **find-min**, and **pairs** functions.  Each function has a comment describing its intended behavior, along with some example uses.

## Testing

You can test your implementations by running the command `lein test` from the root directory of the project.  The tests are in the file `test/clojure_quiz/core_test.clj`.

If you would like to run a Clojure REPL to test your functions, run the command `lein repl` from the root of the project, and then run the command `(use 'clojure-quiz.core)`.  E.g.:

<pre>
[dhovemey@lobsang]$ <b>lein repl</b>
nREPL server started on port 41636 on host 127.0.0.1 - nrepl://127.0.0.1:41636
REPL-y 0.3.1
Clojure 1.6.0
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
 Results: Stored in vars *1, *2, *3, an exception in *e

user=> <b>(use 'clojure-quiz.core)</b>
nil
user=> <b>(add-to-all [1 2 3] 4)</b>
"I CAN HAZ HIGHER ORDER FUNCTION?"
</pre>

You can also use the Counterclockwise REPL by typing Control-Alt-S when you are editing the `core.clj` file.  (Once the REPL is active, you can type Control-Alt-S again to have the REPL reload your functions.)

# Submitting

If you are using Eclipse, and have the Simple Marmoset Uploader plugin installed, just select the **clojure-quiz** project in the package explorer, click the blue up arrow icon, and enter your Marmoset username and password.

You can also submit from the command line by running the command `make submit` from the root of the project.
