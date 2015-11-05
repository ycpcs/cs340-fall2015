---
layout: default
title: "Lab 14: Introduction to Ruby"
---

Getting Started
===============

Download [CS340\_Lab14.zip](CS340_Lab14.zip) and unzip it.

You will modify the program **sched.rb**.

To run the program, start a terminal, change to the directory containing **sched.rb**, and run the command

    ruby sched.rb

Course Scheduling
=================

Your task is to write a Ruby program that reads information about courses and their prerequisites, and prints information about the courses.

The course information is in a file called **courses.txt**. Each line represents one course. Here is an example of one line:

    1340,CS 340 - Programming Language Design,1201

Each line lists a course number, course name, and (optionally) the course number of its immediate prerequisite. (Course numbers are simply unique integer identifiers.) The line above lists the information about CS 340, indicating that its unique course number is 1340, and that it depends on a course whose course number is 1201 (which happens to be CS 201.)

You should define a **Course** class to represent information about a single course. This class might look something like the following:

{% highlight ruby %}
class Course
    attr_reader :num, :name, :direct_prereq

    def initialize(num, name, direct_prereq)
        @num = num
        @name = name
        @direct_prereq = direct_prereq
    end
end
{% endhighlight %}

The use of **attr\_reader** automatically generates getter methods for the **@num**, **@name**, and **@direct\_prereq** fields. For example, if **c** is a reference to a **Course** object, then **c.num** would return the value of the **@num** field for that object.

For example, a **Course** object could be created as

{% highlight ruby %}
cs101 = Course.new(1101, 'CS 101 - Introduction to Computer Science I', nil)
{% endhighlight %}

The idea is that the **@direct_prereq** field will be the course number of the direct prerequisite, or **nil** value if there is no direct prerequisite. In the example above, there is no direct prerequisite. Creating a **Course** object for a course with a direct prerequisite might look something like

{% highlight ruby %}
cs201 = Course.new(1201, 'CS 201 - Introduction to Computer Science II', 1101)
{% endhighlight %}

since **1101** is the course number for the **CS 101** course, which is the direct prerequisite for **CS 201**.

First Task
==========

For the first task, your program should read the information in **courses.txt** and simply print it out, so that for each course, a course number, course name, and direct prerequisite is printed.

Example output:

<pre>
Course number: 1100
  Name: CS 100 - CPADS
  Direct prerequisite: 0
Course number: 1101
  Name: CS 101 - Introduction To Computer Science I
  Direct prerequisite: 0
Course number: 1201
  Name: CS 201 - Introduction To Computer Science II
  Direct prerequisite: 1101
<i>more output...</i>
</pre>

Hints
-----

Opening a file:

{% highlight ruby %}
f = File.new("courses.txt")
{% endhighlight %}

Iterating through the lines of the file:

{% highlight ruby %}
f.each_line do |line|
    # line is a string containing one line of the file
end
{% endhighlight %}

You can split a string **s** into an array of strings (in this case separated by commas) as follows:

{% highlight ruby %}
arr = s.split(',')
{% endhighlight %}

The **to\_i** method converts a string to an integer.

Second Task (challenging!)
==========================

For the second task, your program should read **courses.txt** and then print a report of the transitive prerequisites of all courses.

A course's transitive prerequisites is a set that contains

-   all of the course's immediate prerequisites
-   all of the transitive prerequisites of the course's immediate prerequisites

For example, CS 340 has CS 201 as its immediate prerequisite, and CS 201 has CS 101 as its immediate prerequisite. So, the transitive prerequisites of CS 340 are CS 101 and CS 201.

Here is what the output should look like:

    ### Transitive prerequisites ###
    CS 100 - CPADS: 
    CS 101 - Introduction To Computer Science I: 
    CS 201 - Introduction To Computer Science II: 1101
    CS 320 - Software Engineering and Design: 1101, 1201
    CS 330 - Network Applications and Protocols: 1101, 1201
    CS 340 - Programming Language Design: 1101, 1201
    CS 350 - Data Structures: 1101, 1201
    CS 360 - Algorithms: 1101, 1201
    CS 370 - Computer Graphics Programming I: 1101, 1201
    CS 420 - Operating Systems: 2260, 1101, 1201
    CS 481 - Senior Software Project I: 1320, 1101, 1201
    ECE 260 - Fundamentals of Computer Engineering: 1101, 1201
    MAT 171 - Calculus I: 
    MAT 172 - Calculus II: 3171
    MAT 235 - Discrete Mathematics: 3171, 3172

Note that a course's transitive prerequisites do not need to be listed in any particular order.

Hints
-----

Here is a possible algorithm for finding transitive prerequisites

    done = false
    while !done
        done = true
    
        for each course c
            for each of the course's prerequisites p
                add p's prerequisites as prerequisites of c
            end for
    
            if c's prerequisites changed
                done = false
            end if
        end for
    end while

A hash mapping course numbers to **Course** objects will probably be useful.

The Ruby **Set** class may be useful for keeping track of sets of prerequisites:

> <http://www.ruby-doc.org/stdlib-1.9.3/libdoc/set/rdoc/Set.html>
