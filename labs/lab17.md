---
layout: default
title: "Lab 17: Functional programming exercises in Scala"
---

Getting Started
===============

Download [lab17.scala](lab17.scala) into any directory. Change directory to that directory. Run the program using the command

    scala lab17.scala

Functional Programming Exercises
================================

In this lab, you will gain some more experience with functional programming in Scala by completing the definitions of a series of methods.

As you work on each function, you can remove the line of code that reads

{% highlight scala %}
throw new RuntimeException("TODO")
{% endhighlight %}

There are tests in the **main** function of the **Lab17** class. You can uncomment each test as you complete each function.

General hints
-------------

-   think about a base case for each recursive function
-   you can use **list.head** to get the first element of a list and **list.tail** to get a list containing the remaining elements of a list (after the first)
-   use **element :: list** to construct a new list by prepending **element** to **list**

Task 1
------

Complete the definition of the **allEven** function, which takes a list of integers and returns true if all of the elements are even, false otherwise.

Task 2
------

Complete the definition of the **reverse** function, which takes a list of integers and returns a list with the elements of the original list reversed.

This function has a nested helper function **reverseWork**, which is the part you will need to implement. This function should be tail-recursive: the *accum* parameter is used to build up the partial results of the computation.

Task 3
------

Complete the definition of the **makeIntList** function, which takes an integer **n** and returns a list containing all of the integers from 1 to **n**, inclusive.

This function has a nested helper function **makeIntListWork**, which is the part you need to implement.

Task 4
------

Complete the definition of the **replaceMultiplesOf** function, which takes a list of integers and an integer **n**, and returns a list in which each element of the original list that is *both*

-   greater than **n**
-   an exact multiple of **n**

is replaced by 0.

This function has a nested helper function **replaceMultiplesOfWork**, which is the part you need to implement.

Task 5
------

Complete the definition of the **allPrimesLessThan** function, which takes an integer **n** and returns a list of all of the prime numbers less than **n**.

The idea is that you can repeatedly call **replaceMultiplesOf** to change the multiples of 2, 3, 4, etc. to 0. After all such multiples have been replaced, filter the list to remove all of the 0 elements.
