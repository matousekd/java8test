package cz.matousekd.java8test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author David.Matousek
 * @since 17.6.2017
 */
public class Java8Generics {

    public static void main(String[] args) {
        Java8Generics generics = new Java8Generics();
        //unbounded wildcards
        List<String> strings = new ArrayList<>();
        strings.add("test");
        generics.unboundedWildcardsUsage(strings);
        //upped bound wildcards
        List<Integer> ints = new ArrayList<>();
        ints.add(3);
        generics.upperBoundedWildcardsUsage(ints);
        //lower bounded wildcards
        List<Number> numbers = new ArrayList<>();
        numbers.add(23);
        generics.lowerBoundedWildcardsUsage(1, numbers);



    }

    private void unboundedWildcardsUsage(List<?> stuff) {
        //will fail
//        stuff.add("abc");
//        stuff.add(new Object());
//        stuff.add(3);
        System.out.println("Undefined list size:" + stuff.size());
    }

    public void upperBoundedWildcardsUsage(List<? extends Number> numbers) {
        //will also fail
//        numbers.add(new Integer(3));
//        numbers.add(3.14159);
//        numbers.add(new BigDecimal("3"));
        System.out.println("Integer list size:" + numbers.size());
    }

    public void lowerBoundedWildcardsUsage(Integer num, List<? super Integer> output) {
        output.add(1);
        output.add(3);
        System.out.println("Numbers list size:" + output.size());
    }

}


