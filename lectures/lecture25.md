---
layout: default
title: "Lecture 25: Introduction to Haskell"
---

Example code: [mandelbrot.hs](mandelbrot.hs), [better-complex.hs](better-complex.hs)

Really, really excellent Haskell Tutorial: [Learn You a Haskell for Great Good](http://learnyouahaskell.com/) by Miran Lipovača (this is also available as a book)

Haskell
=======

A pure, lazy, statically-typed functional language.

Pure: there are no side effects (such as assignments to variables).

Lazy: results of expressions are not computed until they are needed. Note that ubiquitous lazy evaluation would be dangerous in a language with side effects, but without side effects it is always safe because the values of variables never change.

Statically-typed: the types of all values and functions are determined at compile time. Haskell supports a very powerful system of *type inference*, which means that types can often be determined automatically without explicit annotation from the programmer.

Data types and functions
========================

Haskell has the usual built-in data types that we would expect to see in a functional language: numbers, tuples, and lists.

Tuples &mdash; fixed-length sequences of values &mdash; are used for user-defined data types. Because Haskell is statically-typed, each element of a tuple can be defined as having a specific type.

A type declaration can be used to give a name to a tuple with particular combination of types.

Functions are the essential building blocks of programs in a functional programming language. Haskell functions are much like the functions we have seen in the other functional programming langauges (such as Scala, Erlang, and Clojure).

Example: Complex Numbers
------------------------

{% highlight haskell %}
type Complex = (Double, Double)

cmake :: Double -> Double -> Complex
cmake r  i = (r, i)

creal :: Complex -> Double
creal (r, i) = r

cimag :: Complex -> Double
cimag (r, i) = i

cadd :: Complex -> Complex -> Complex
cadd (a, b) (c, d) = (a+c, b+d)

cmul :: Complex -> Complex -> Complex
cmul (a, b) (c, d) = (a*c - b*d, b*c + a*d)

cmag :: Complex -> Double
cmag (r, i) = sqrt (r*r + i*i)
{% endhighlight %}

The **type** declaration declares the type **Complex** as being another name for a tuple with two **Double** values.

Functions in Haskell are declared as follows:

> *functionName* *parameters* = *body*

Since Haskell is a functional language, the body is always an expression which computes a value.

Functions may optionally be preceeded by an explicit type declaration. It has the form:

> *functionName* :: *functionType*

The function type describes the types of the function's parameters and return type. The arrow (**-\>**) describes a function by decribing its input and output types. Functions with multiple parameters are actually modeled by a series of functions: more on this later.

Note that the functions that operate on complex numbers are specified as taking a tuple consisting of two values. For example:

{% highlight haskell %}
cmag (r, i) = sqrt (r*r + i*i)
{% endhighlight %}

The **cmag** function takes a single parameter, which is a tuple consisting of values **r** and **i**. So, we can specify function parameters as a pattern, in much the same way as in Prolog and Erlang.

Example: Mandelbrot set
-----------------------

We can decide whether or not a complex number *C* is in the Mandelbrot set by iterating the equation

> *Z* = *Z*<sup>2</sup> + *C*

where *Z* is initially 0 + 0*i*. We stop when either the magnitude of *Z* reaches 2, or the number of iterations reaches a predetermined threshold. If the iteration count reaches the threshold, the complex number is assumed to be in the set.

Here is a **citer** function which computes an iteration count for a specified complex number:

{% highlight haskell %}
citer :: Complex -> Int
citer c = citer_work c (0, 0) 0

citer_work :: Complex -> Complex -> Int -> Int
citer_work c z count =
    if ((count >= 1000) || (cmag z) >= 2.0)
        then count
        else citer_work c (cadd c (cmul z z)) (count + 1)
{% endhighlight %}

Note that we have defined a tail-recursive helper function called **citer\_work** to do the actual computation. As with Erlang, tail recursion is the fundamental way to implement iteration in Haskell without causing unbounded growth of the activation record stack.

The **Row** type describes a row of complex numbers sharing a common imaginary value (y), and being evenly spaced starting at a given real value (xmin) and separated by a given value (dx):

{% highlight haskell %}
-- rownum, y, xmin, dx, numcols
type Row = (Int, Double, Double, Double, Int)
{% endhighlight %}

Note that **--** introduces a comment, which continues to the end of the line.

Note that pattern matching is the only general-purpose way to access the values in a tuple. If we want a convenient way to extract information from a **Row** value, we will need accessor functions. These can be defined as follows:

{% highlight haskell %}
row_rownum :: Row -> Int
row_rownum row =
    let (rownum, _, _, _, _) = row
      in rownum

row_y :: Row -> Double
row_y row =
    let (_, y, _, _, _) = row
      in y

row_xmin :: Row -> Double
row_xmin row =
    let (_, _, xmin, _, _) = row
      in xmin

row_dx :: Row -> Double
row_dx row =
    let (_, _, _, dx, _) = row
      in dx

row_numcols :: Row -> Int
row_numcols row =
    let (_, _, _, _, numcols) = row
      in numcols
{% endhighlight %}

As with Prolog, Erlang, and Clojure, an underscore can be used to indicate a variable whose value will not be used.

[Note that there is an easier way to define accessors, as we will see later.]

The **RowResult** type is a list of iteration counts for a given row (identified by its row number):

{% highlight haskell %}
-- rownum, list of iteration counts
type RowResult = (Int, [Int])
{% endhighlight %}

Note that the syntax

> [*type*]

means a list of values of the specified type.

The **compute\_row** function takes a **Row**, calls **citer** for each complex number specified by the row, and returns a **RowResult** containing the computed iteration counts:

{% highlight haskell %}
compute_row :: Row -> RowResult
compute_row row = compute_row_work row ((row_numcols row) - 1) []

compute_row_work :: Row -> Int -> [Int] -> RowResult
compute_row_work row i accum =
    if (i < 0)
        then
           let rownum = (row_rownum row)
               in (rownum, accum)
        else
            let c = cmake ((row_xmin row) +
                           ((fromIntegral i) * (row_dx row)))
                          (row_y row)
            in compute_row_work row (i - 1) ((citer c) : accum)
{% endhighlight %}

Again, a tail-recursive helper function is used to do the actual computation. Node that the iteration counts are computed backwards, since a tail-recursive computation to build a list will generate the list in reverse order.

The syntax

> *value* : *list*

performs the Haskell version of the *cons* operation: prepending a value onto a list.

Note that **Int** values must be converted using the **fromIntegral** function before they can be combined with **Double** values in an operation.

The **let** construct in Haskell assigns values to local variables: it is very similar to **let** in Clojure.

Finally, we can compute iteration counts for evenly-spaced points in a rectangular region of the complex plane with the **compute** function:

Testing in **ghci**, the interactive Haskell interpreter (note: output formatted for clarity):

Note that because Haskell does not use commas to separate function parameters, we must parenthesize negative values. Otherwise, arguments such as

> 2 -2

would be interpreted as a single value (2 - 2).

Better record types
===================

Since records are used frequently, Haskell offers a way to automatically generate constructors and accessors for record types. Here is a better way to define a complex number type:

{% highlight haskell %}
data Complex = Complex { real :: Double,
                         imag :: Double } deriving Show
{% endhighlight %}

Note that **deriving Show** allows **Complex** values to be displayed automatically (for example, when evaluating expressions interactively in the interpreter).

This syntax gives us a better way of constructing **Complex** values, and also defines accessor functions **real** and **imag** for accessing the real and imaginary components of a complex number:

Note that the constructor syntax allows the values to be specified in any order, as long as all of the required values are specified.

Defining an operation:

{% highlight haskell %}
cadd :: Complex -> Complex -> Complex
cadd l r = Complex { real=(real l) + (real r), imag=(imag l) + (imag r) }
{% endhighlight %}

Partial Evaluation, Currying, and Composition
=============================================

Recall that the type of a function can be expressed with the syntax

> *inputType* -\> *outputType*

and that functions with multiple parameters have types that are expressed with a chain of arrows (-\>). This is because a function with multiple parameters can be *partially evaluated*. This is what it sounds like: a function passes fewer arguments than parameters, and the result is a function that expects the remaining parameters.

Simple example:

<pre>
Prelude> <b>let addtwo left right = left + right</b>
Prelude> <b>addtwo 1 2</b>
3
Prelude> <b>let addone = (addtwo 1)</b>
Prelude> <b>addone 4</b>
5
</pre>

Here, we define **addtwo** as a function taking two parameters, **left** and **right**, and returning their sum. When applied to two arguments (1 and 2), we get the expected result (3).

The interesting part is the **addone** function: it is created by applying **addtwo** to a single argument, 1. This effectively binds the argument (1) to **addtwo**'s first parameter (**left**), while leaving its second parameter (**right**) unbound. The result is a function that takes a single parameter (**right**) and returns the sum 1 + **right**. When applied to the argument **4**, this yields the result 5.

The idea that a function of multiple parameters can be built from a series of functions of a single parameter is called *currying*. (This term is in honor of the logician [Haskell Curry](http://en.wikipedia.org/wiki/Haskell_Curry), for whom the Haskell language is also named.)

*Composition* is the counterpart to partial evaluation: it is the idea that functions of a single parameter can be combined to create a function with multiple parameters.

Simple example:

<pre>
*Main> <b>let add1 = (+1)</b>
*Main> <b>let mult2 = (*2)</b>
*Main> <b>let add1AndMult2 = (mult2 . add1)</b>
*Main> <b>add1AndMult2 4</b>
10
</pre>

Note that **(+1)** and **(\*2)** are partial applications of the **+** and **\*** operators, respectively.

The dot (**.**) operator composes two functions. The expression **(mult2 . add1)** yields a function which invokes **add1** on its argument and then invokes **mult2** on the result of calling **add1**.

Partial evaluation and composition are powerful ways to build new functions out of existing functions.

There is much, much more
========================

Obviously, there is far more to learn about Haskell. The book covers some additional topics (list processing, lazy evalaution, monads, etc.) [Learn You a Haskell for Great Good](http://learnyouahaskell.com/) is also a great resource for learning about Haskell.

Interesting software written in Haskell
=======================================

Some important software has been written in Haskell:

-   [Darcs](http://darcs.net/), a distributed version control system
-   [Snap](http://snapframework.com/), a web application framework
-   [Pugs](https://hackage.haskell.org/package/Pugs), an implementation of the Perl6 language
-   [Pandoc](http://pandoc.org/), a "universal document converter"
