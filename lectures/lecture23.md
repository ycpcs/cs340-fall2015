---
layout: default
title: "Lecture 23: Functional programming in Scala"
---

Functional Programming
======================

*Functional programming* is a style of programming characterized by *referential transparancy*.

Referential Transparency
------------------------

Referential transparency means that expressions that compute values can always be replaced by their values without changing the meaning of the program. One way in which an expression can fail to have referential transparency is if the expression causes a side-effect such as an assignment to a mutable variable, reading from an input source, or writing to an output source.

Example: let's say that you see the following expression in a program written in a Java-like programming language:

    f() * 2 + f() * 2

If the function **f** has referential transparency, then the subexpressions **f() \* 2** are guaranteed to yield the same value each time they are evaluated. In this case, it would be legal for the compiler to replace this expression with

    (f() * 2) * 2

because multiplication distributes over addition.

However, if **f** does not have referential transparency, then this optimization would be illegal since it would not return the same result as the original expression. For example, consider two implementations of **f**:

{% highlight java %}
// First implementation
public int f() { return 4; }

// Second implementation
static int x = 3; // a global variable
public int f() { x++; return x; }
{% endhighlight %}

Consider the results of evaluating the original expression and transformed expression for each implementation:

> Expression | First implementation | Second implementation
> ---------- | -------------------- | ---------------------
> `f() * 2 + f() * 2` | `4*2 + 4*2 = 16` | `4*2 + 5*2 = 18`
> `(f() * 2) * 2` | `(4*2) * 2 = 16` | `(4*2) * 2 = 16`

Because the second implementation of **f** does not have referential transparency, each evaluation of **f() \* 2** yields different values, and so the transformed expression does not compute the same result as the original.

Why is this important?
----------------------

Referential transparency has some interesting advantages:

-   More opportunities for compiler optimization
-   [Memoization](http://en.wikipedia.org/wiki/Memoization) can be used to cache the results of previously evaluated expressions
-   Computations can be safely distributed over multiple processors/threads, even if they access shared data (because that data does not change)
-   Programs with referential transparency can be easier to understand because the reader of the program does not have to think about the side-effects an expression might cause

Dealing with complex data
-------------------------

One of the main challenges in functional programming is doing computations involving complex data such as (lists, trees, and graphs) without causing side effects.

In an imperative language, it is always possible to *destructively update* complex data structures. For example, say that we have a sequence of integers and we want to change all occurrences of 0 to 1. Here's one way to do it, in Java:

{% highlight java %}
public static void replaceZeroes(List<Integer> list) {
    ListIterator<Integer> i = list.listIterator();
    while (i.hasNext()) {
        Integer val = i.next();
        if (val == 0) {
            i.set(1);
        }
    }
}
{% endhighlight %}

Given a **List** of integer values as a parameter, the **replaceZeroes** method uses an iterator to find each occurrence of 0 and replace it with a 1. This method is *not* referentially transparent because it destructively modifies the list as a side-effect of calling the method.

Here is a referentially transparent way to perform the same task:

{% highlight java %}
public static List<Integer> replaceZeroes(List<Integer> list) {
    List<Integer> result = new ArrayList<Integer>();
    for (Integer val : list) {
        if (val == 0) {
            val = 1;
        }
        result.add(val);
    }
    return result;
}
{% endhighlight %}

In this version of the method, the original list is not destructively modified. Instead, a new list containing the desired elements is returned.

This leads to a fairly simple way of stating how a referentially transparent operations transform complex data:

> <div class="callout"> Don't modify it; instead, create a new one. </div>

Are we consing yet?
-------------------

One of the most important kinds of compound data is the *list*, which is an arbitrary sequence of values.

Most functional languages use lists very extensively, and have a large number of built-in operations for manipulating lists. Scala is no exception.

The **List** data type in Scala represents a sequence of values. Scala **List** are essentially singly-linked lists. Every list value is either

-   *Nil*, which is the special "empty list" value, or
-   a *cons cell*, which is a single value and a link to another list which contains the rest of the values in the sequence

Each cons cell has accessors **head** and **tail**, where **head** is the value, and **tail** is the link to the list containing the rest of the values.

Here is a referentially-transparent Scala function to replaces all of the 0 values in a list of integers with 1:

{% highlight scala %}
def replaceZeroes(list : List[Int]) : List[Int] = {
  if (list == Nil)
    Nil
  else {
    val first = if (list.head == 0) 1 else list.head 
    first :: replaceZeroes(list.tail)
  }
}
{% endhighlight %}

Note that the **if**/**else** construct in Scala computes a value. The idea is that if the input list is empty (Nil), then the result is an empty list. Otherwise, the result is constructed by prepending either a 1 or the current list head onto a list constructed by calling the function recursively on the rest of the list. Note that the **::** operator prepends a value onto an existing list. Because this operation *cons*tructs a new list by creating a new cons cell, it is often referred to as the *cons* operation.

In most functional languages, recursion is used in rather than explicit iteration to describe repeated computations. One reason is that loops tend to involve assignments to an accumulator variable, or other destructive updates. Another reason is that most complex data types are recursive, so recursive computation is more "natural". We see this explicitly in the definition of the list datatype: a list is either *Nil* (the base case) or a value prepended onto another list (the recursive case). The **replaceZeroes** method exactly conforms to this definition.

Efficiency, Tail Recursion
--------------------------

One concern when using recursion in a computation is the number of recursive calls that are necessary to complete the computation. In the **replaceZeroes** method, the number of calls to **replaceZeroes** will be equal to the number of elements in the list. If the list contains a large number of values, we run the risk of exceeding the maximum size of the call stack.

Most recursive functions can be modified to use *tail recursion* to avoid this problem. A tail-recursive function is one where all recursive calls are in *tail position*, meaning that the result of the recursive call is immediately returned. The compiler can transform a tail call into a jump that simply restarts the current function call with new parameter values.

Note that the recursive call to **replaceZeroes** is *not* in tail position, because its value is appending onto *first* before being returned. A good way of seeing why it is not tail-recursive is to think about how the recursive calls are used to build the result:

{% highlight scala %}
replaceZeroes(List(0, 1, 2, 3))
(1 :: replaceZeroes(List(1, 2, 3)))
(1 :: (1 :: replaceZeroes(List(2, 3))))
(1 :: (1 :: (2 :: replaceZeroes(List(3)))))
(1 :: (1 :: (2 :: (3 :: replaceZeroes(Nil)))))
(1 :: (1 :: (2 :: (3 :: Nil))))
(1 :: (1 :: (2 :: List(3))))
(1 :: (1 :: List(2, 3)))
(1 :: List(1, 2, 3))
List(1, 1, 2, 3)
{% endhighlight %}

None of the cons (**::**) operations can be performed until the final recursive call to **replaceZeroes** is evaluated.

Transforming a function into tail-recursive form generally requires an *accumulator parameter* to build up the result of the computation.

Here is a possible tail-recursive implementation called **replaceZeroes2**:

{% highlight scala %}
def replaceZeroes2(list : List[Int]) : List[Int] = {
  @tailrec
  def replaceZeroesWork(list : List[Int], accum : List[Int]) : List[Int] = {
    if (list == Nil)
      accum
    else {
      val first = if (list.head == 0) 1 else list.head
      replaceZeroesWork(list.tail, first :: accum)
    }
  }

  replaceZeroesWork(list, Nil)
}
{% endhighlight %}

Note that a nested helper function is used. The **@tailrec** annotation declares that the **replaceZeroesWork** method must be compiled using the tail-call optimization.

Note that there is a problem: **replaceZeroes2** does not compute the same result as **replaceZeroes**! If we test both with the code:

{% highlight scala %}
println(replaceZeroes(List(0, 1, 2, 3)))
println(replaceZeroes2(List(0, 1, 2, 3)))
{% endhighlight %}

We get the output:

{% highlight scala %}
List(1, 1, 2, 3)
List(3, 2, 1, 1)
{% endhighlight %}

which shows that **replaceZeroes2** has reversed the values in the list!

Here is what is happening when **replaceZeroes2(List(0, 1, 2, 3))** is evaluated:

{% highlight scala %}
replaceZeroes2(List(0, 1, 2, 3))
replaceZeroesWork(List(0, 1, 2, 3), Nil)
replaceZeroesWork(List(1, 2, 3), List(1))
replaceZeroesWork(List(2, 3), List(1, 1))
replaceZeroesWork(List(3), List(2, 1, 1))
replaceZeroesWork(Nil, List(3, 2, 1, 1))
List(3, 2, 1, 1)
{% endhighlight %}

The problem is that prepending the values onto the accumulator effectively reverses the elements.

One solution to this problem is to simply call the **reverse** method on the accumulator before returning it:

{% highlight scala %}
def replaceZeroes2(list : List[Int]) : List[Int] = {
  @tailrec
  def replaceZeroesWork(list : List[Int], accum : List[Int]) : List[Int] = {
    if (list == Nil)
      accum.reverse
    else {
      val first = if (list.head == 0) 1 else list.head
      replaceZeroesWork(list.tail, first :: accum)
    }
  }

  replaceZeroesWork(list, Nil)
}
{% endhighlight %}
