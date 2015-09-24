---
layout: default
title: "Assignment 4: Clojure MOOC"
---

**Due**:

* Milestone 1 is due Monday, Oct 5th
* Milestone 2 is due Monday, Oct 12th
* Milestone 3 is due Thursday, Oct 22nd

**Note**: This assignment will not be graded.

# Learning Clojure

We will be using [Clojure](http://clojure.org/) for a series of programming assignments.

The [Functional programming with Clojure](http://mooc.fi/courses/2014/clojure/index.html) MOOC at the University of Helsinki is a truly excellent way to learn Clojure.  In this assignment you will work on the first four chapters.  There are two Milestones:

* Milestone 1: Complete **Basic tools**, **Training day**, and **I am a horse in the land of booleans**
* Milestone 2: Complete **Structured data** and **P-P-P-Pokerface** (look over **Style** as well, although there are no problems in that chapter)
* Milestone 3: Complete **Predicates** and **Recursion**

You can find the chapters on the [Material and course content](http://iloveponies.github.io/120-hour-epic-sax-marathon/index.html) page.

# Programming environment

The **Basic tools** chapter covers the software you will need.

I recommend using Eclipse with the [Counterclockwise](https://code.google.com/p/counterclockwise/) plugin.  You can install this through the Eclipse marketplace (search for "counterclockwise").

I also recommend installing the Local Terminal plugin: this allows you to run command line commands from within Eclipse, including running Clojure interactively.  You can find Local Terminal by choosing **Help&rarr;Install new software...**, then choosing the Luna (or Juno, etc.) update site, then choosing **General Purpose Tools&rarr;Local Terminal**.  Note that local terminal only works on Linux and Mac OS.  (If you are using Windows, you might consider running Linux in a virtual machine.)

As an alternative to Eclipse and Counterclockwise, you can try installing [Light Table](http://www.lighttable.com/).

The computers in KEC 119 have Eclipse with Counterclockwise and Local Terminal.

# Installing Leiningen

The version of Leiningen installed by default in KEC 119 does not work.  Follow these steps to install a working version of Leiningen:

    cd
    mkdir -p bin
    cd bin
    wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
    chmod a+x lein

Also, make sure that your `$HOME/bin` directory appears earlier than the system executable directories on your `$PATH`.  You should be able to run the command

    which lein

and the output should be something like

    /home/username/bin/lein

If it says `/usr/bin/lein`, then you need to fix your `$PATH`.  One approach is to add the following code to your `.bashrc` file:

    if [ -z "$ADDED_HOME_BIN_TO_PATH" ]; then
        export ADDED_HOME_BIN_TO_PATH=yes
        export PATH=$HOME/bin:$PATH
    fi

(Note: your `.bashrc` file should be located in your home directory, and if it doesn't exist, you should create it.)

Also: if it is possible that you have executed the wrong (system) version of the `lein` command at any point, then you should delete your `$HOME/.lein` directory:

    cd
    rm -rf .lein

# Using Git

The programming activities for each chapter are in a Git repository on GitHub.  The recommended way of starting each chapter is to fork the repository, and then clone your fork.

For example, after forking the **training-day** repository, I would clone my fork using the command

    cd
    cd git
    git clone git@github.com:daveho/training-day

This would place the repository in the `git` subdirectory of my home directory (which is the recommended place to put local Git repositories.)

# Importing the repository into your Eclipse workspace

If you are planning to use Eclipse and Counterclockwise, you will need to import the repository as an Eclipse project.  To do so:

1. Go the to Git perspective in Eclipse.
2. In the Git Repositories view, click the "Add an existing local Git repository" button, and choose the local Git repository (e.g., **training-day**).
3. Switch back to the Java perspective.
4. Choose **File&rarr;New&rarr;Project...&rarr;General&rarr;Project**.
5. Uncheck the "Use default location" checkbox.  Use "Browse" to choose the directory containing your local Git repository (e.g., **training-day**).  Enter the project name (e.g., **training-day**.)
6. Click "Finish".  You should now see the project in your Eclipse workspace.  The Clojure file you will need to edit will be in the **src** directory.
7. Right-click on the project and choose **Configure&rarr;Convert to Leiningen Project**.  (The "Progress Information" dialog may hang: just cancel it if this happens.)

Once you have set up the Eclipse project, you should commit your changes and push them to your fork.  In general, it is a good idea to commit and push your changes to your GitHub repository as you work: your GitHub repository will serve as a record of your progress.

# Testing your work

To run the unit tests for a programming activity:

1. Start a Local Terminal, click the "Connect" icon, then run the command <code>cd <i>path-to-repo</i></code>, where <i>path-to-repo</i> is the path to the local git repository containing your work.  (E.g., `git/training-day`.)
2. Next, run the command <code>lein midje</code>.  The output will indicate how many tests passed and which tests (if any) failed.

If you get an error from Leiningen about `midje` not being a valid task, try deleting the `.lein` directory (in your home directory) and running the command again.

To test your functions interactively (highly recommended!):

1. Start a Local Terminal and cd into the repository directory (as described above).
2. Run the command `lein repl` to start a read-eval-print loop.
3. Enter <code>(use '<i>namespace</i>)</code>, where *namespace* is the namespace defined for the chapter, e.g., `i-am-a-horse-in-the-land-of-booleans`
4. Now you can call functions defined in your source code.  Note that you will need to exit and restart the REPL if you change your code.
