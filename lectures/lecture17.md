---
layout: default
title: "Lecture 17: Ruby"
---

Designed by Yukihiro Matsumoto in 1993; first public release in 1995.

Features
========

Dynamically typed (variables may contain any kind of value)

Interpreted: Ruby source code is directly loaded into the Ruby interpreter and executed. (Internally, the interpreter translates the source program into a more-easily executed format.)

Because it is dynamically typed and interpreted, Ruby is often referred to as a "scripting" language. The designation "scripting language" brings connotations of not being a "serious" language, and/or not being suitable for the development of large programs. This view is too narrow; many large programs are now developed using languages like Ruby, Python, Perl, etc. At the same time, type checking in dynamically-typed languages is generally deferred to runtime, meaning that some type errors may not be caught until the program runs. Opinions differ as to whether or not this is a serious problem.

Everything is an Object
=======================

Ruby is a pure object-oriented language; all values are references to objects. For example, each of the following two statements prints "5"

{% highlight ruby %}
puts 2 + 3
puts 2.+(3)
{% endhighlight %}

The expression "2 + 3" is familiar: literal values 2 and 3 are added by an infix + operator. In most languages, values 2 and 3 would belong to a primitive integer type distinct from the universe of classes and user-defined types. In Ruby, they are instances of the Fixnum class. The second code example above makes a direct call to the + method on the object 2, passing the argument 3.

We can directly inquire the class an object belongs to by calling the "class" method.

{% highlight ruby %}
puts 2.class
{% endhighlight %}

This prints "Fixnum".

Blocks
======

Methods in Ruby can take a code block as an argument. You can think of a code block as being an anonymous procedure passed to the called method; the called method will then call the block procedure as part of its execution.

Ruby uses blocks in many contexts where some operation is being performed on a sequence of values. For example, we can compute a factorial this way:

{% highlight ruby %}
c = 1
(1..6).each do |n| c *= n end
puts c
{% endhighlight %}

Note that the value (1..6) is an instance of the Range class. Its "each" method invokes a code block for each member of the range, passing the member as the argument to the block. The code block in this code is the part between "do" and "end"; the block has a single parameter "n" which receives the value of each member of the range in sequence.

Note that we haven't declared any variable in this program: variables are created automatically as needed. (In other words, the first applied occurrence of a variable is its binding occurrence.) An uninitialized variable has a special "nil" value (like a null pointer in Java/C++).

Ruby Literals
=============

Kinds of literal values in Ruby:

The usual numeric literals:

> |Kind|Examples|Class|
> |----|--------|-----|
> |fixed-precision integer|4, 17, 42|Fixnum|
> |arbitrary-precision integer|1111111111111111111111111111111|Bignum|
> |floating-point|001, 3.14159, 10e7|Float|

All literal text, including single characters and strings of characters, are treated as Strings:

> |Kind|Examples|Class|
> |----|--------|-----|
> |Single-quoted string|'a', 'hello world'|String|
> |Double-quoted string|"a", "hello world\\n"|String|

Double-quoted strings support a variety of escape sequences to represent special characters. Examples:

> <table>
> <col width="29%" />
> <col width="43%" />
> <thead>
> <tr class="header">
> <th align="left">Escape sequence</th>
> <th align="left">Meaning</th>
> </tr>
> </thead>
> <tbody>
> <tr class="odd">
> <td align="left">\n</td>
> <td align="left">Newline character</td>
> </tr>
> <tr class="even">
> <td align="left">\r</td>
> <td align="left">Carraige-return character</td>
> </tr>
> <tr class="odd">
> <td align="left">\f</td>
> <td align="left">Form-feed character</td>
> </tr>
> <tr class="even">
> <td align="left">\b</td>
> <td align="left">&quot;Bell&quot;</td>
> </tr>
> <tr class="odd">
> <td align="left">\\</td>
> <td align="left">Literal backslash character</td>
> </tr>
> <tr class="even">
> <td align="left">\&quot;</td>
> <td align="left">Literal double-quote character</td>
> </tr>
> </tbody>
> </table>

Single-quoted strings only support two escape sequences:

> |Escape sequence|Meaning|
> |---------------|-------|
> |\\'|Literal single-quote character|
> |\\\\|Literal backslash character|

Symbol literals are like enumeration values. They are members of the Symbol class:

> |Kind|Examples|Class|
> |----|--------|-----|
> |symbol value|:foobar, :dog, :cat|Symbol|

Regular Expressions
===================

Regular expression literals:

> / *regular expression* /

A regular expression's class is Regexp.

Example:

{% highlight ruby %}
r = /foo|bar/
puts r.class
puts r.match('foo') ? 'yes' : 'no'
puts r.match('bar') ? 'yes' : 'no'
puts r.match('foobaz') ? 'yes' : 'no'
puts r.match('blat') ? 'yes' : 'no'
{% endhighlight %}

This code produces the output

    Regexp
    yes
    yes
    yes
    no

The syntax and meaning of Ruby regular expressions is very similar to the syntax and meaning of [Perl Regular Expressions](http://perldoc.perl.org/perlre.html).

Ignoring case in a regular expression
-------------------------------------

The "i" modifier causes a regular expression object to match case-insensitively:

{% highlight ruby %}
#! /usr/bin/ruby

r = /foobar/i;

if r.match('foobar')
    puts "yes"
end

if r.match('FOObaR')
    puts "yes"
end
{% endhighlight %}

This program outputs

    yes
    yes

Regexp search and replace
-------------------------

The gsub method of the String class replaces all occurrences of substrings matching a regular expression with a given replacement string.

Example: removing HTML tags from a string.

{% highlight ruby %}
#! /usr/bin/ruby

matchtag = /<[^>]*>/;

STDIN.each do |line|
    # Remove newline from end of line
    line.chomp!

    # Replace all occurrences of HTML tags from the string
    line = line.gsub(matchtag, '')

    # Print out line
    puts line
end
{% endhighlight %}

String interpolation
====================

A double-quoted string can have the textual representations of values stored in variables automatically substituted in the string. For example:

{% highlight ruby %}
s = 'Alice'
puts "Hi #{s}"
{% endhighlight %}

produces the output

    Hi Alice

Note that arbitrary expressions may be used:

{% highlight ruby %}
n = 4
puts "n + 5 is #{n + 5}"
{% endhighlight %}

The result of the expression is converted to a string (by calling the **to\_s** method) before being substituted into the result string.

Classes and methods
===================

Ruby classes and methods work more or less the same way as in C++, Java, and other object-oriented languages.

{% highlight ruby %}
class Animal
end

class Dog < Animal
    def noise
        puts "Bark"
    end
end

class Cat < Animal
    def noise
        puts "Meow"
    end
end

fifi = Dog::new
brutus = Cat::new

fifi.noise
brutus.noise
{% endhighlight %}

Ruby uses the convention that an identifier beginning with an at symbol (@) denotes a field:

{% highlight ruby %}
class Animal
    def initialize(name)
        @name = name
    end
end

class Dog < Animal
    def noise
        puts "#{@name}: Bark"
    end
end

class Cat < Animal
    def noise
        puts "#{@name}: Meow"
    end
end

fifi = Dog::new("Fifi")
brutus = Cat::new("Brutus")

fifi.noise
brutus.noise
{% endhighlight %}

Note that Animal's constructor method (initialize) is inherited by both Dog and Cat.

From these examples you can see that Ruby is not too different than Java and C++ in the way it supports object-oriented programming. Except...

Dynamic Object-Oriented Features in Ruby
========================================

Classes and objects in Ruby are much more dynamic and flexible than Java or C++.

For example, we can add a method to an existing object at runtime:

{% highlight ruby %}
def fifi.walkies
    puts "#{@name} goes for walkies"
end
{% endhighlight %}

The method call

{% highlight ruby %}
fifi.walkies
{% endhighlight %}

produces the output

    Fifi goes for walkies

However, the code

{% highlight ruby %}
rex = Dog::new("Rex")
rex.walkies
{% endhighlight %}

Produces the following error:

    ./animals.rb:34: undefined method `walkies' for #<Dog:0x100ef49c @name="Rex"> (NoMethodError)

because plain instances of the Dog class do not support the walkies method.

We can also define a method within a class whose purpose is to handle all method invocations where a method cannot be found through normal virtual dispatch.

Example:

{% highlight ruby %}
#! /usr/bin/ruby

class All_Method
    def method_missing(method, *args)
        method_name = method.to_s
        print method_name
        args.each do |arg|
            print " #{arg.to_s}"
        end
        print "\n"
    end
end
{% endhighlight %}

The code

{% highlight ruby %}
a = All_Method::new

a.hi_there
{% endhighlight %}

produces the output

    hi_there

The code

{% highlight ruby %}
a.i_can_do_anything 'a', 'b', 'c'
{% endhighlight %}

produces the output

    i_can_do_anything a b c

Using this technique, we can define classes that decide what kinds of methods they support at run time rather than compile time.

Note that this would be difficult/impossible in a language that requires static type checking.
