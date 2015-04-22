package uk.me.williammartin.illuminator;

public class Man extends Human {

    public final static String DEFAULT_NAME = "JOHN DOE";
    
    public Man() {
        this(DEFAULT_NAME);
    }
    
    public Man(String name) {
        super(name);
    }

    public Man(String name, int age) {
        super(name, age);
    }
    
}
