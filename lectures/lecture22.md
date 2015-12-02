---
layout: default
title: "Lecture 22: Scala"
---

Scala
=====

Scala is a hybrid language that supports both object-oriented and functional styles of programming. It was invented by [Martin Odersky](http://lampwww.epfl.ch/~odersky/).

Scala programs are compiled into java class files, and run on the Java virtual machine. A Scala program can use Java classes, including the many built-in classes that ship with the JVM.

Scala is statically-typed, meaning that each variable and value has a data type that is known at compile time. Java is also statically typed. Unlike Java, Scala programs do not need to specify types explicitly in situations where a type can be inferred automatically. This makes Scala programs significantly more concise than Java programs.

Functional programming
----------------------

*Functional* programming is a programming style characterized by:

-   Immutable variables and data structures
-   The use of functions with no side-effects
-   Higher-order functions (functions that return functions)

One way to understand what is meant by "immutable variables and data structures" and "no side-effects" is to think about what it would be like to program in a language in which there was no assignment operator. Each variable in the program would have an initial value, but that value could not be changed.

Functional programming has two advantages over imperative programming (where variables and data structures are mutable):

-   the program can be easier to understand
-   parallelizing the program to take advantage of multiple processors is easier, because with immutable data, there is no danger of one processor interfering with another processor's data by changing its value

There are some disadvantages of functional programming:

-   Output is difficult to express (since it is a kind of side effect)
-   Some algorithms are more efficient and/or use less memory if they are allowed to mutate data

Scala is not a pure functional language, since it does support mutable data. This allows the programmer to use both functional and imperative styles where they make the most sense.

Scala data types
----------------

Scala has many of the same fundamental of data types as Ruby:

-   numbers
-   booleans
-   ranges
-   lists

See textbook for details and syntax.

Scala supports code blocks that are very similar to those supported by Ruby. For example, ranges and lists have methods such as **foreach**, **count**, and **map** that allow a sequence of values to be transformed with a code block.

Example: computing a factorial by multiplying a series of integers:

{% highlight scala %}
(1 to 6).reduceLeft( (a, b) => a * b )
{% endhighlight %}

**(1 to 6)** is an inclusive range. The **reduceLeft** method is very similar to Ruby's **inject** method: it starts by applying the code block to the first two values in the range, and then continues applying the previously computed value to the next value in the range.

Scala classes
-------------

Scala classes are like Java classes: they define a data type where the values are objects with fields and methods.

Scala classes use a much more compact syntax than Java classes. In particular, a Scala class definition defines

-   the class
-   the "primary" constructor
-   the fields

all in one. For example:

{% highlight scala %}
class SuperHero(name : String, hates : List[String], canBeat : List[String]) {
    def getName : String = name

    def hates(other : String) : Boolean = hates.contains(other)

    def canBeat(other : String) : Boolean = canBeat.contains(other)
}
{% endhighlight %}

An instance of the **SuperHero** class has three fields: **name**, **hates**, and **canBeat**, and that the **SuperHero** class's constructor requires values for each of these fields as parameters. This eliminates a significant amount of redundancy that would occur in a Java program. Here is the equivalent Java class:

{% highlight java %}
public class SuperHero {
    private String name;
    private List<String> hates;
    private List<String> canBeat;

    public SuperHero(String name, List<String> hates, List<String> canBeat) {
        this.name = name;
        this.hates = hates;
        this.canBeat = canBeat;
    }

    public String getName() {
        return name;
    }

    public boolean hates(String other) {
        return hates.contains(other);
    }

    public boolean canBeat(String other) {
        return canBeat.contains(other);
    }
}
{% endhighlight %}

Notice that to define an initialize the **name** field, the identifier **name** appears in four different places!

Scala objects can be created and used in much the same way as Java objects:

{% highlight scala %}
object SuperHeroTest {
    def main(args: Array[String]) {
        // Create some SuperHeros
        val particleMan = new SuperHero("Particle Man", List(), List())
        val personMan = new SuperHero("Person Man", List(), List())

        val triangleMan = new SuperHero(
            "Triangle Man",
            List("Particle Man", "Person Man"),
            List("Particle Man", "Person Man"))

        // Triangle Man hates Person Man
        println(triangleMan.hates(particleMan.getName))

        // They have a fight: Triangle wins
        println(triangleMan.canBeat(particleMan.getName))
    }
}
{% endhighlight %}

Note that **SuperHeroTest** is an *object*, not a class. Essentially, **SuperHeroTest** is a data type that has only one instance. Only Scala object types can define a **main** method.

A complete example
------------------

The first section of the textbook suggests evaluating Tic Tac Toe boards as a self-test exercise.

To represent the board, we will use a list of list of **Char** values, which is the list of rows in the board. This type is written **List[List[Char]]**. A **Board** object is constructed from the list of lists. Here is the class definition / main constructor:

{% highlight scala %}
class Board(rows : List[List[Char]]) {
{% endhighlight %}

A **Board** object has just one field, **rows**.

The **Board** class has methods which query the board to determine if a particular piece ('X' or 'O') has won. Checking for a winner involves checking the rows, columns, and diagonals, to see if one of them has all three values set to the same value as the piece we're checking.

We can simplify our win-checking code by creating a method that checks an arbitrary list of characters to see if the first three members are equal to a specified piece value:

{% highlight scala %}
def win(seq : List[Char], piece : Char) : Boolean = {
  seq.count( c => c == piece ) == 3
}
{% endhighlight %}

Note that the **count** method of **List** executes an arbitrary code block on each member of the list, and counts the number of times the code block returns a true value.

Given the **win** method, checking all rows of the board is easy:

{% highlight scala %}
rows.count( r => win(r, piece) ) > 0
{% endhighlight %}

Again, we use the **count** method, this time to check whether the **win** method reported a true result for at least one row of the board.

Checking columns is slightly more complicated, since the board is stored as rows. We introduce a method called **column** which, given the index of a column (0-2), returns a list of characters representing that column:

{% highlight scala %}
def column(index : Int) : List[Char] = {
  rows.map( r => r(index) )
}
{% endhighlight %}

The **map** method of **List** applies a code block to the sequence of values in the list, and returns a list containing the results. In the **column** method, we map a block which selects, for each row, the member of the row whose index is **index**. (Note that Scala uses parantheses instead of square brackets to access an element of a **List**.)

Now that we have a method that returns a column, it is easy to check columns:

{% highlight scala %}
(0 until 3).count( i => win(column(i), piece) ) > 0
{% endhighlight %}

Here, the **count** method is used on the range **0 until 3** to count the number of columns where the **win** method returns true.

Diagonals require some thought to handle. Here is a method called **diag** that returns a **List** of characters representing one of the board's diagonals. The starting column and row are specified, as well as the column and row increments needed to generate the other column/row locations in the diagonal:

{% highlight scala %}
def diag(x : Int, y : Int, dx : Int, dy : Int) : List[Char] = {
  (0 until 3).map( i => rows(y + i*dy)(x + i*dx) ).toList
}
{% endhighlight %}

In a way that is similar to the **column** method, the **diag** method uses a range to generate all three column/row pairs for the diagonal, and uses **map** to generate a list containing the characters in the diagonal. The diagonals are checked for a winning configuration using the code:

{% highlight scala %}
win(diag(0, 0, 1, 1), piece) ||
win(diag(2, 0, -1, 1), piece)
{% endhighlight %}

In other words, check the upper-left to lower-right diagonal and the upper-right to lower-left diagonal.

Here is the entire **Board** class:

{% highlight scala %}
class Board(rows : List[List[Char]]) {
    def isWinner(piece : Char) : Boolean = {
      rows.count( r => win(r, piece) ) > 0 ||
        (0 until 3).count( i => win(column(i), piece) ) > 0 ||
        win(diag(0, 0, 1, 1), piece) ||
        win(diag(2, 0, -1, 1), piece)
    }

    def column(index : Int) : List[Char] = {
      rows.map( r => r(index) )
    }

    def diag(x : Int, y : Int, dx : Int, dy : Int) : List[Char] = {
      (0 until 3).map( i => rows(y + i*dy)(x + i*dx) ).toList
    }

    def win(seq : List[Char], piece : Char) : Boolean = {
      seq.count( c => c == piece ) == 3
    }
}
{% endhighlight %}

This is a remarkably compact piece of code, especially compared to the equivalent Java code, which would probably require 2 or 3 times more lines of code.
