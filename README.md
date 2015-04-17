# Illuminator
Illuminator is a simple Reflection library for Java. It is both an attempt to avoid the verboseness of the standard Reflection library and an exercise in learning for the author.

# Getting started with Illuminator

Illuminator is in an extremely early form right now. To get ahold of, and to test the code please follow these instructions.

First, clone this repository:

    git clone https://github.com/williammartin/illuminator.git

Then, navigate into the illuminator project directory and run:

    mvn test

You must have maven installed and available on your path or the previous step will fail.

# How do I use it?

Right now, Illuminator is in very early stages and doesn't support much functionality. Here is an example of how you might create an instance of a Class.
    
```java
import uk.me.williammartin.illuminator.Illuminator;
    
SomeClass someObject = Illuminator
                        .illuminate(SomeClass.class)
                        .construct();
```

You can also pass arguments to the constructor as simply as:
    
```java
import uk.me.williammartin.illuminator.Illuminator;
    
SomeClass someObject = Illuminator
                        .illuminate(SomeClass.class)
                        .construct("Hello", "World!");
```

Good luck!



