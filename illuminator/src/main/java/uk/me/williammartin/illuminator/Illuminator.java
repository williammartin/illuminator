package uk.me.williammartin.illuminator;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * @author William Martin This is the entry point for the Illuminator library.
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

    // Let's find a constructor that is an exact match for the args passed in
    private Constructor<?> findConstructor(Object... args) throws IlluminatorException {

        Class<?>[] argTypes = getTypes(args);

        try {
            return clazz.getConstructor(argTypes);
        } catch (NoSuchMethodException e) {
            throw new IlluminatorException(e);
        }
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
