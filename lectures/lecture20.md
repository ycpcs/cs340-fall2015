---
layout: default
title: "Lecture 20: Ruby Arrays, Hashes, Mixins"
---

Example code:

> [numlist.rb](numlist.rb) - defining a class with an **each** method
>
> [findmin.rb](findmin.rb) - using **inject** to find the minimum value in a sequence

Ruby Arrays
===========

Ruby arrays - instances of the **Array** class. Documentation:

> <http://www.ruby-doc.org/core-1.9.3/Array.html>

Arrays have a literal notation - a series of comma-separated values within square brackets:

{% highlight ruby %}
arr = [1, 2, 3]
{% endhighlight %}

Elements may be accessed using the subscript operator

{% highlight ruby %}
puts arr[0]    # prints "1"
{% endhighlight %}

The **each** method is the usual way to iterate over the values in an array:

{% highlight ruby %}
arr.each {|n| puts n } # prints "1", "2", "3" on separate lines
{% endhighlight %}

Ruby Hashes
===========

Ruby hashes: instances of the **Hash** class. Documentation:

> <http://www.ruby-doc.org/core-1.9.3/Hash.html>

Hashes associate a set of keys with corresponding values. As with arrays, hashes have a literal notation - a comma-separated list of key/value pairs in curly braces:

{% highlight ruby %}
hash = { 'oranges' => 12, 'bananas' => 7, 'blueberries' => 45 }
{% endhighlight %}

To access the value associated with a key, use the subscript operator:

{% highlight ruby %}
puts hash['oranges']    # prints "12"
{% endhighlight %}

Iterating over the keys and/or values can be done with the **each\_key**, **each\_pair**, and **each\_value** methods. Note that because a **Hash** object is implemented as a hash table, there is no guaranteed ordering of keys:

{% highlight ruby %}
hash.each_key {|key| puts key }

# prints "blueberries", "bananas", "oranges"
# on separate lines
{% endhighlight %}

Mixins
======

A *mixin* is a module (collection of methods) that may be added ("mixed in") to any class or object.

One important Ruby mixin is **Enumerable**. Many of the useful methods in the built-in **Array** class are actually provided by **Enumerable**.

The **Enumerable** mixin provides a number of methods for operating on sequences of values. This mixin requires the class or object to support the **each** method, which given a block generates all of the members of the sequence.

Example: the built-in **Array** class uses the **Enumerable** mixin to provide a number of methods. For example, the **inject** method invokes a block for each element in the collection. The value computed by the block is then passed as an argument to the invocation of the block on the *next* element, until the end of the sequence is reached, at which point the result of the last invocation of the block is the overall result.

For example, we can use inject to compute the sum of an array of numbers:

{% highlight ruby %}
arr = [9, 6, 8, 1, 5]
sum = arr.inject(0) {|sum, i| sum + i}
puts sum    # prints 29
{% endhighlight %}

Note that the initial value of **sum** (0), which is the variable that is "carried" by the computation, is passed explicitly to the **inject** method. If we call the **inject** method without an argument, then it would use the first element of the sequence for this value, and start by invoking the block on the *second* element of the sequence. So, computing the sum could also be done as

{% highlight ruby %}
arr.inject {|sum, i| sum + i}
{% endhighlight %}

Another insanely useful method added by the **Enumerable** mixin is **map**, which returns an array formed by invoking a block on each element of the sequence and appending the result to the array. For example:

{% highlight ruby %}
arr = [1, 2, 3]
arr2 = arr.map {|n| n * 2}
arr2.each {|n| puts n}  # prints "2", "4", "6" on separate lines
{% endhighlight %}
