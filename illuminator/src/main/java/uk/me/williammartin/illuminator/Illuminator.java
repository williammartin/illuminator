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
    public static <R> Illuminator<R> illuminate(Class<R> clazz) {
        return new Illuminator<R>(clazz);
    }
    
    /**
     * This is a static utility method and an entry point for illuminating on
     * classes using string names
     *
     * @param  className
     *             The fully qualified name of the class to illuminate
     * @return An instance of Illuminator wrapping the class to illuminate
     */
    @SuppressWarnings("unchecked")
    public static <R> Illuminator<R> illuminate(String className) {
        try {
            Class<R> type = (Class<R>) Class.forName(className);
            return illuminate(type);
        } catch (Exception e) {
            throw new IlluminatorException(e);
        }
    }   
    
    private final Class<T> clazz;

    private Illuminator(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * The construct method corresponds loosely to a constructor.newInstance(),
     * creating a new object of the class the Illuminator object is wrapping
     *
     * @param args
     *            The arguments to be passed to the class constructor
     * @return A new object of the class the Illuminator object is wrapping
     * @throws IlluminatorException
     *             thrown if a constructor cannot be found or class cannot be
     *             instantiated for some reason
     */
    @SuppressWarnings("unchecked")
    public <S extends T> S construct(Object... args) throws IlluminatorException {
        args = toArray(args);
        try {
            Constructor<?> constructor = findConstructor(args);
            return (S) constructor.newInstance(args);
        } catch (Exception e) {
            throw new IlluminatorException(e);
        }
    }
    
    // Constructor searching method:
    //    1. Find an exact matching constructor based on types
    //    2. Look for first constructor that matches close argument types e.g. boxed primitives
    private Constructor<?> findConstructor(final Object... args) throws IlluminatorException {
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

    private static boolean doArgTypesMatch(Class<?>[] constructorTypes, Class<?>[] passedTypes) {

        for (int i = 0; i < constructorTypes.length; i++) {

            Class<?> constructorType = constructorTypes[i];
            Class<?> passedType = passedTypes[i];

            Class<?> wrappedConstructorType = ClassUtils.primitiveToWrapper(constructorType);

            if (passedType != null // skip if null, return the first constructor that matches the rest of the args types
                && !wrappedConstructorType.isAssignableFrom(passedType)) {
                return false;
            }
        }

        return true;
    }

    // Convert the objects args to their respective class types
    private static Class<?>[] getTypes(Object... args) {

        Class<?>[] classes = Arrays
                                 .stream(args)
                                 .map(arg -> arg == null ? null : arg.getClass()) // we can try to match even with a null arg type
                                 .toArray(Class<?>[]::new);

        return classes;
    }

    // Passing null to a varargs method gives a null rather than an array containing null.
    // This fixes that.
    private static Object[] toArray(Object... args) {
        return (args == null ? new Object[1] : args);
    }
}
