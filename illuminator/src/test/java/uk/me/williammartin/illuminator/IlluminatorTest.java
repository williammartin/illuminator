package uk.me.williammartin.illuminator;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IlluminatorTest {
    
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void constructShouldInstantiateClassWithNoArguments() {

        Man testMan = Illuminator
                          .illuminate(Man.class)
                          .construct();

        Assert.assertEquals(Man.DEFAULT_NAME, testMan.getName());
    }

    @Test
    public void constructShouldInstantiateClassAndPassSingleArgument() {

        String NAME = "Robert Paulson";

        Man testMan = Illuminator
                          .illuminate(Man.class)
                          .construct(NAME);

        Assert.assertEquals(NAME, testMan.getName());
    }

    @Test
    public void constructShouldInstantiateClassAndPassPrimitiveArgument() {

        String NAME = "Robert Paulson";
        int AGE = 30;

        Man testMan = Illuminator
                          .illuminate(Man.class)
                          .construct(NAME, AGE);

        Assert.assertEquals(NAME, testMan.getName());
        Assert.assertEquals(AGE, testMan.getAge());
    }

    @Test
    public void constructShouldDoSomethingSensibleWithNullArguments() {
        
        Object[] varargs = null; 

        Man testMan = Illuminator
                          .illuminate(Man.class)
                          .construct(varargs);
        
        Assert.assertEquals(null, testMan.getName());
    }
    
    @Test
    public void illuminateShouldParseStringClassNameSuccessfully() {

        String NAME = "Robert Paulson";
        int AGE = 30;
        
        Man testMan = Illuminator
                         .illuminate("uk.me.williammartin.illuminator.Man")
                         .construct(NAME, AGE);

        Assert.assertEquals(NAME, testMan.getName());
        Assert.assertEquals(AGE, testMan.getAge());
    }
    
    @Test
    public void illuminateShouldThrowReflectionExceptionIfTypesClash() {

        expectedEx.expect(IlluminatorException.class);
        
        @SuppressWarnings("unused")
        Man testMan = Illuminator
                        .illuminate("uk.me.williammartin.illuminator.Human")
                        .construct();
    }
}
