# Illuminator [![Build Status](https://travis-ci.org/williammartin/illuminator.svg?branch=master)](https://travis-ci.org/williammartin/illuminator)

[![Join the chat at https://gitter.im/williammartin/illuminator](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/williammartin/illuminator?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
Illuminator is a simple Reflection library for Java. It is both an attempt to avoid the verboseness of the standard Reflection library and an exercise in learning for the author.

# Getting started with Illuminator

Illuminator is in an extremely early form right now. To get ahold of, and to test the code please follow these instructions.

First, clone this repository:

    git clone https://github.com/williammartin/illuminator.git

Then navigate into the illuminator project directory. Your next steps depend on whether you choose to run locally or in a VM.

## Running Locally

*Requirements:*
- Java 8
- Maven

Run:

    mvn test

## Using Vagrant

*Requirements:*

- Vagrant
- VirtualBox

Run:

    vagrant up
    vagrant ssh
    mvn test

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



