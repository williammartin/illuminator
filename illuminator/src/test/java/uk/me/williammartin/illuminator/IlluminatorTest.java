package uk.me.williammartin.illuminator;

import junit.framework.Assert;

import org.junit.Test;

public class IlluminatorTest {

    @Test
    public void constructShouldInstantiateClassWithNoArguments() {

        Person testPerson = Illuminator
                                .illuminate(Person.class)
                                .construct();

        Assert.assertEquals(Person.DEFAULT_NAME, testPerson.getName());
    }

    @Test
    public void constructShouldInstantiateClassAndPassSingleArgument() {

        String NAME = "Robert Paulson";

        Person testPerson = Illuminator
                                .illuminate(Person.class)
                                .construct(NAME);

        Assert.assertEquals(NAME, testPerson.getName());
    }

    @Test
    public void constructShouldInstantiateClassAndPassPrimitiveArgument() {

        String NAME = "Robert Paulson";
        int AGE = 30;

        Person testPerson = Illuminator
                                .illuminate(Person.class)
                                .construct(NAME, AGE);

        Assert.assertEquals(NAME, testPerson.getName());
        Assert.assertEquals(AGE, testPerson.getAge());
    }

    @Test
    public void constructShouldDoSomethingSensibleWithNullArguments() {

        Person testPerson = Illuminator
                                .illuminate(Person.class)
                                .construct(null);

        Assert.assertEquals(null, testPerson.getName());
    }

    @Test
    public void illuminateShouldParseStringClassNameSuccessfully() {

        String NAME = "Robert Paulson";
        int AGE = 30;

        Person testPerson = (Person) Illuminator
                                .illuminate("uk.me.williammartin.illuminator.Person")
                                .construct(NAME, AGE);

        Assert.assertEquals(NAME, testPerson.getName());
        Assert.assertEquals(AGE, testPerson.getAge());
    }
}
