---
layout: default
title: "Lab 9: Clojure data structures"
---

# Getting started

Start a new Clojure project in Eclipse using **New &rarr; Project &rarr; Clojure &rarr; Clojure Project**.  Name the new project **lab9**.

Open the file **src/lab9/core.clj**.  Create your functions in this file.  When you are ready to test your functions, start a REPL by right-clicking in the editor and choosing **Clojure &rarr; Load file in REPL**.  When you modify your code, do the same thing to reload your definitions in the REPL.

If you would prefer to use Light Table, or edit your code in a text editor and run the Clojure REPL from the command line, feel free.

# Your task

Complete the exercises shown below.

## Word count

Write a function named `count-words`.  It should take a sequence of strings as a parameter, and return a map indicating, for each word in the input sequence, how many occurrences of that word there were.

Requirement: your function must be tail-recursive.  Suggestion: use the `loop` construct, where the loop variables are the current sequence of words and the current accumulator (the partially constructed of words to word counts.)

Example calls:

    => (count-words ["clojure" "is" "fun" "and" "jello" "is" "yummy"])
    {"yummy" 1,
     "jello" 1,
     "and" 1,
     "fun" 1,
     "is" 2,
     "clojure" 1}
    
    => (count-words ["hey" "ya" "hey" "ya"])
    {"ya" 2, "hey" 2}

Hints:

* [empty?](https://clojuredocs.org/clojure.core/empty_q) checks a collection (such as a sequence) to see whether it is empty
* [first](https://clojuredocs.org/clojure.core/first) gets the first element of a sequence
* [rest](https://clojuredocs.org/clojure.core/rest) gets the remaining elements of a sequence
* `{}` is an empty map
* [assoc](https://clojuredocs.org/clojure.core/assoc) adds or changes a key/value associated in a map, returning a new map as a result
* [contains?](https://clojuredocs.org/clojure.core/contains_q) checks a map to see if it contains a specified value
* [get]() retrieves the value associated with a key (note that there is a variant that allows you to specify a default value, to be used if the map doesn't contain the key)

## Sort by frequency

Write a function called `sort-by-frequency`.  It should take a sequence of strings as a parameter, and return a sequence of strings as a result.  The result sequence should contain the strings in the original sequence, sorted by frequency, so that the most frequent strings are returned first.  To break ties (when two words occur with equal frequency), the strings themselves should be compared.  So, if "apples" and "oranges" occur with equal frequency, "apples" should be ordered before "oranges" because "apples" compares as less than "oranges".

Example calls:

    => (sort-by-frequency ["apples" "apples" "lemons" "pears" "apples" "oranges" "oranges" "lemons"])
    ["apples" "lemons" "oranges" "pears"]
    => (seq (sort-by-frequency ["pump" "up" "the" "jam" "pump" "it"]))
    ("pump" "it" "jam" "the" "up")

Note how in the first example, "lemons" is earlier in the result sequence than "oranges": their counts are the same (2), but the string "lemons" compares as less than "oranges".

Here are some hints.

Have `sort-by-frequency` call `count-words`.

Use the `seq` function to convert the map of strings to counts into a sequence.  Each member of the sequence will be a vector whose first element is a string (word) and whose second element is an integer (count).

Sort the sequence of string/integer vectors using the [sort](http://clojuredocs.org/clojure.core/sort) function.  Here is a comparator function that will probably be useful:

{% highlight clojure %}
(defn pair-gt [[lkey lval] [rkey rval]]
  (let [valcmp (compare rval lval)
        keycmp (compare lkey rkey)]
    (if (= valcmp 0)
      keycmp
      valcmp)))
{% endhighlight %}

This is essentially a Clojure version of a Java comparator: it compares two key/value pairs.  The Clojure `compare` function (used to compare the keys and values in the two pairs being compared) is like Java's `compareTo` method, returning a negative value if the left value is less than the right, 0 if they are equal, and a positive value if the left value is greater than the right.

Once the key/value pairs are sorted, use the `map` or `mapv` functions to extract just the first element from each pair in the sorted sequence of pairs.
