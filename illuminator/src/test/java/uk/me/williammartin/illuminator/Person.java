package uk.me.williammartin.illuminator;

public class Person {

    public static final String DEFAULT_NAME = "JOHN DOE";

    private final String name;
    private int age;

    public Person() {
        this(DEFAULT_NAME);
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
