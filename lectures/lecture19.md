---
layout: default
title: "Lecture 19: Metaprogramming in Ruby"
---

Code examples: [meta.rb](meta.rb), [meta2.rb](meta2.rb)

Blocks and Procs
================

In Ruby, you can create an object that is a handle to a block by creating a **Proc** object:

{% highlight ruby %}
add1 = Proc.new {|x| x + 1}
puts add1.call(3)               # prints 4
{% endhighlight %}

A **Proc** acts as an anonymous function.

Metaprogramming
===============

*Metaprogramming* is the ability for a program to create new functions/methods when the program runs.

Ruby has excellent support for metaprogramming because

-   Methods can be added to classes and objects at runtime
-   **Proc**s allow code to be "saved" and executed later

Motivation
----------

Most classes will need getter and setter methods:

{% highlight ruby %}
class Person
    def initialize(name, age)
        @name = name
        @age = age
    end

    def getName
        return @name
    end

    def setName(name)
        @name = name
    end

    def getAge
        return @age
    end

    def setAge(age)
        @age = age
    end
end
{% endhighlight %}

Defining getters and setters is tedious: they all look the same! They are also a source of unnecessary bugs: if we accidentally refer to the wrong field within a getter or setter, it won't work as expected.

Metaprogramming to the rescue
-----------------------------

Wouldn't it be nice if we could *generate* getters and setters as needed?

Recall two unusual characteristics of Ruby:

-   All classes are "open": the program can add new methods to any class at any time
-   Class declarations can contain executable statements: these are treated as method calls on the class object

Example:

{% highlight ruby %}
#! /usr/bin/ruby

class Object
    def self.sayhello
        puts "Hello!"
        puts "self.class is #{self.class}"
        puts "This class is #{self.name}"
    end
end

class Person
    sayhello
end

class Vehicle
    sayhello
end
{% endhighlight %}

The output of the program is

    Hello!
    This class is Person
    Hello!
    This class is Vehicle

Things to note:

-   This code adds a new class method (i.e., static method) called **sayhello** to the built-in **Object** class, which is a superclass of every other class
-   The **Person** and **Vehicle** classes call this method in their declarations
-   When executed, the **sayhello** method is called on the **Class** object for the class being declared

Now, the stage is set for metaprogramming: what if a class called a method as part of its declaration (like the calls to **sayhello** above, but that method *added more methods to the class*? Now we are metaprogramming!

Let's use metaprogramming to generate getters and setters for fields whose names are specified by a list of symbols.

{% highlight ruby %}
#! /usr/bin/ruby

class Object
    def self.gen_getters_and_setters(*names)
        names.each do |name|
            fieldname_sym = "@#{ name }".to_sym

            setter = Proc.new do |value|
                self.instance_variable_set(fieldname_sym, value)
            end
            self.send(:define_method, "set_#{ name }", setter)

            getter = Proc.new do
                return self.instance_variable_get(fieldname_sym)
            end
            self.send(:define_method, "get_#{ name }", getter)
        end
    end
end

class Person
    gen_getters_and_setters :name, :age

    def initialize(name, age)
        @name = name
        @age = age
    end

    # Note: getters and setters not defined explicitly!
end

p = Person::new("Dave", 41)
puts "Original age is: #{p.get_age}"

p.set_age(42)
puts "Happy birthday, your age is now #{p.get_age}"
{% endhighlight %}

A few things to note:

-   The asterisk on the \***names** parameter allows it to capture a variable number of arguments
-   The **instance\_variable\_set** and **instance\_variable\_get** methods allow access to a field whose name is specified as a symbol (which we compute by constructing a string equal to the field name and converting the string to a symnol)
-   The **define\_method** method of the **Class** class defines a new instance method. Because it is a private method, we can't call it directly, but instead must use the class object's **send** method to call it indirectly. (This appears to be necessary because **define\_method** is private, and can't be called directly.)

Metaprogramming in real life
============================

In practice, you won't have to define facilities like the **gen\_getters\_and\_setters** method described above. Ruby already provides similar facilities: see **attr\_reader** and **attr\_accessor** in Ruby's [Module](http://ruby-doc.org/core-1.9.3/Module.html) class. (**Module** is the superclass of **Class**: so, these facilities are available to all Ruby classes and mixins.)
