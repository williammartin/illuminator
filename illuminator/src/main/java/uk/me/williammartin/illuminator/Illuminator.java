package uk.me.williammartin.illuminator;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.ClassUtils;

/**
 * This is the entry point for the Illuminator library.
 * 
 * @author William Martin 
 * @param <T>
 */
public class Illuminator<T> {

    /**
     * This is a static utility method and the entry point for illuminating on
     * classes
     * 
     * @param clazz
     *            The class to illuminate
     * @return An instance of Illuminator wrapping the class to illuminate
     */
    public static <T> Illuminator<T> illuminate(Class<T> clazz) {
        return new Illuminator<T>(clazz);
    }

    private final Class<T> clazz;

    private Illuminator(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * The construct method corresponds loosely to a constructor.newInstance(),
     * creating a new object of the class the Illuminator object is wrapping
     * 
     * Currently this cannot deal with primitive arguments
     * 
     * @param args
     *            The arguments to be passed to the class constructor
     * @return A new object of the class the Illuminator object is wrapping
     * @throws IlluminatorException
     *             thrown if a constructor cannot be found or class cannot be
     *             instantiated for some reason
     */
    @SuppressWarnings("unchecked")
    public T construct(Object... args) throws IlluminatorException {

        try {
            Constructor<?> constructor = findConstructor(args);
            return (T) constructor.newInstance(args);
        } catch (Exception e) {
            throw new IlluminatorException(e);
        }
    }

    // Constructor searching method:
    //    1. Find an exact matching constructor based on types
    //    2. Look for first constructor that matches close argument types e.g. boxed primitives
    private Constructor<?> findConstructor(Object... args) throws IlluminatorException {

        Class<?>[] argTypes = getTypes(args);

        try {
            return clazz.getConstructor(argTypes);
        } catch (NoSuchMethodException e) {
            
            Constructor<?>[] constructors = clazz.getConstructors();
            
            Optional<Constructor<?>> firstMatchingConstructor = Arrays
                                                                   .stream(constructors)
                                                                   .filter(constructor -> constructor.getParameterCount() == args.length)
                                                                   .filter(constructor -> doArgTypesMatch(constructor.getParameterTypes(), argTypes))
                                                                   .findFirst();
            
            return firstMatchingConstructor.orElseThrow(() -> new IlluminatorException(e));
        }
    }
    
    private boolean doArgTypesMatch(Class<?>[] constructorTypes, Class<?>[] passedTypes) {
        
        for (int i = 0; i < constructorTypes.length; i++) {
            
            Class<?> constructorType = constructorTypes[i];
            Class<?> passedType = passedTypes[i];
            
            Class<?> wrappedConstructorType = ClassUtils.primitiveToWrapper(constructorType);
            
            if (!wrappedConstructorType.isAssignableFrom(passedType)) {
                return false;
            }   
        }
        
        return true;
    }
    
    // Convert the objects args to their respective class types
    private Class<?>[] getTypes(Object... args) {

        Class<?>[] classes = Arrays
                                 .stream(args)
                                 .map(arg -> arg.getClass())
                                 .toArray(Class<?>[]::new);

        return classes;
    }
}
