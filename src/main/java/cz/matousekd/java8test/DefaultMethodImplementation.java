package cz.matousekd.java8test;

/**
 * @author David.Matousek
 *
 */
public interface DefaultMethodImplementation {

    void someMethod();

    default void methodWithDefaultImplementation() {
        System.out.println("default implementation");
    }

    public static String aStaticSomething() {
        return "Static method in interface";
    }
}
