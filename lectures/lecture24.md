---
layout: default
title: "Operator overloading, actors in Scala"
---

Operator Overloading in Scala
=============================

Scala supports *operator overloading*, which means that the meaning of operators (such as `*` and `+`) may be defined for arbitrary types.

Example: a complex number class:

{% highlight scala %}
class Complex(val real : Double, val imag : Double) {
  def +(other : Complex) = new Complex(
    real + other.real,
    imag + other.imag)

  def *(other : Complex) = new Complex(
    real*other.real - imag*other.imag,
    imag*other.real + real*other.imag)

  def magnitude() : Double = Math.sqrt(real*real + imag*imag)
}
{% endhighlight %}

Code that uses this class:

{% highlight scala %}
var C = new Complex(x, y)
var Z = new Complex(0.0, 0.0)

var count = 0

while (count < THRESHOLD && Z.magnitude() < 2.0) {
  Z = Z*Z + C
  count += 1
}
{% endhighlight %}

This code determines whether or not a complex number *C* is in the [Mandelbrot Set](http://en.wikipedia.org/wiki/Mandelbrot_set).

Actors in Scala
===============

Actor example code: [actor.scala](actor.scala), [actor2.scala](actor2.scala)

*Actors* are the main mechanism in Scala for expressing *concurrency*.

An actor is an object that waits to receive *messages*, which are arbitrary Scala objects/values. Each actor processes the messages it receives sequentially. (A queue called the *mailbox* is used to keep track of the messages sent to an actor.)

Because actors communicate by sending messages to each other, rather than modifying shared data structures, they are potentially a more robust and easy-to-use means for expressing concurrency and parallelism than threads.

Defining an actor class
-----------------------

An actor class should extend the **Actor** base class. Here is an example:

{% highlight scala %}
case object SayHello

class Hello(val name : String) extends Actor {
  def act() {
    loop {
      react {
        case SayHello => {
          println(name + " says hello")
          exit()
        }
      }
    }
  }
}
{% endhighlight %}

A **Hello** actor will wait to receive a **SayHello** message. (**SayHello** is a *case object*, which is essentially a singleton object with no behavior.) When the message is received, it prints the message "*name* says hello" and then exits.

Sending a message
-----------------

Messages are sent to actors using the **!** operator, with the syntax

Here's a test program using the **Hello** actor class above:

{% highlight scala %}
object Main {
  def main(args : Array[String]) {
    val a = new Hello("Alice")
    a.start()

    val b = new Hello("Bob")
    b.start()

    a ! SayHello
    b ! SayHello
  }
}
{% endhighlight %}

Note that actors must be started using the **start()** method before they start listening for messages.

If you run this program a few times, you will notice that the output does not appear in a predictable order. For example, on my computer:

This is because when a message sent,

-   the sender does not wait for a reply, and
-   each actor runs concurrently with other actors, so messages processed by different actors could be processed in any order

Because actors process messages concurrently, they can be used for *parallel computation*, where parts of a computation are executed simultaneously on different processors/cores.

Waiting for a reply
-------------------

Sometimes it is useful to be able to send a message to an actor and then wait for a reply. This is possible using the **!!** operator.

Example: here is an actor which accepts **Add** messages containing two integer values, computes the sum, and sends the sum back to the sender:

{% highlight scala %}
case class Add(val x : Int, val y : Int)

class Adder extends Actor {
  def act() {
    loop {
      react {
        case msg : Add => {
          val sum = msg.x + msg.y
          sender ! sum
          exit()
        }
      }
    }
  }
}
{% endhighlight %}

Notice that when the sum is computed, it is immediately sent to *sender*, which is a function that identifies the sender of a received message.

Here is a main program which creates an **Adder** actor and uses it to add two integers:

{% highlight scala %}
object Main {
  def main(args : Array[String]) {
    val adder = new Adder
    adder.start()

    val f = adder !! new Add(3, 4)
    val resultUntyped = f()
    resultUntyped match {
      case sum : Int => {
        println("sum is " + sum)
      }
    }
  }
}
{% endhighlight %}

Here is a quick explanation of what is happening:

1.  The message **new Add(3, 4)** is sent to the actor using **!!**
2.  The result of the **!!** operator is a *future*: a function which, when called, will (eventually) produce a value
3.  The result of calling the future **f()** is an value whose type is *Any*, which is similar to *Object* in Java in the sense that it could represent any kind of value. This accounts for the fact that we can't predict what kind of reply message the actor will send.
4.  The **match** construct is used on the untyped result: matching it against the **Int** data type allows the result to be converted to an integer

