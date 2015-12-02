---
layout: default
title: "Lab 18: Mandelbrot set in Scala"
---

Mandelbrot Set in Scala
=======================

Download [mandelbrot.scala](mandelbrot.scala).

Sequential version
------------------

Complete the **compute** method in the **Row** class.

It should generate a series complex numbers

> real=(xStart+i\*dx), imaginary=y

where **i** is a value between 0 (inclusive) and **numPoints** (exclusive).

Call each complex number *C*. For each value of *C*, determine how many times the equation

> Z = Z\*Z + C

can be iterated before the magnitude of some value of *Z* reaches 2, or the maxium number of iterations is reached.

The result of the **compute** method should be the list of iteration counts for each value of *C*.

To test, run the program:

    scala mandelbrot.scala

It should produce output similar to the following:

    0: List(1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    1: List(1, 1, 1, 2, 2, 2, 2, 2, 1, 1)
    2: List(1, 1, 2, 3, 3, 3, 2, 2, 2, 1)
    3: List(1, 3, 3, 4, 6, 18, 4, 2, 2, 2)
    4: List(1, 3, 7, 7, 1000, 1000, 9, 3, 2, 2)
    5: List(1, 1000, 1000, 1000, 1000, 1000, 7, 3, 2, 2)
    6: List(1, 3, 7, 7, 1000, 1000, 9, 3, 2, 2)
    7: List(1, 3, 3, 4, 6, 18, 4, 2, 2, 2)
    8: List(1, 1, 2, 3, 3, 3, 2, 2, 2, 1)
    9: List(1, 1, 1, 2, 2, 2, 2, 2, 1, 1)

Parallel version using actors
-----------------------------

Use actors to compute each row in parallel.

Add the following imports to the top of the source file:

{% highlight scala %}
import scala.actors.Actor
import scala.actors.Actor._
{% endhighlight %}

Suggested approach: define a **RowActor** class:

Then, define a **Mandelbrot** actor class. Its job should be to create a **RowActor** for each row that needs to be computed, and send it one row as a message. It should receive messages for completed rows from the **RowActor**s. When all rows are completed, it should print out all of the completed rows in order (similar to the original program output.)

You can change the code in the **main** function to something like the following:

{% highlight scala %}
val m = new Mandelbrot(x1, y1, x2, y2, cols, rows)
m.start()
m ! Compute
{% endhighlight %}

Here, we're assuming that the **Mandelbrot** actor accepts a **Compute** message that will start the computation. You can define this as a case object:

{% highlight scala %}
case object Compute
{% endhighlight %}

Hints
=====

You may want to define a **CompletedRow** class, something like the following:

{% highlight scala %}
class CompletedRow(val rowNum : Int, val counts : List[Int]) {
}
{% endhighlight %}

A **CompletedRow** object represents the finished iteration counts for a particular row, along with the row number. This can be used as the type of message that **RowActor**s send to the **Mandelbrot** actor.

The **Mandelbrot** actor can prepend (cons) each **CompletedRow** object to a list. When the list is complete, the **CompletedRow**s can be sorted by row number.
