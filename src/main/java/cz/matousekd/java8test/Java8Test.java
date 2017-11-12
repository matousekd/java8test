package cz.matousekd.java8test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * @author David.Matousek
 */
public class Java8Test {

    private final List<String> aList = Arrays.asList(new String[]{"a", "b", "c", ""});

    public static void main(String[] args) throws InterruptedException {

        Java8Test java8Test = new Java8Test();
        java8Test.noArgumentsLambdaExample();
        java8Test.argumentsLambdaExample();
        java8Test.functionClassesExample();
        java8Test.lambdaCollectionsExample();
        java8Test.methodReference();
        DefaultMethodImplementation methodImplementation = new DefaultMethodImplementation() {
            @Override
            public void someMethod() {

            }
        };
        methodImplementation.methodWithDefaultImplementation();
        System.out.println(DefaultMethodImplementation.aStaticSomething());
        java8Test.typeAnnotationExample();
        java8Test.newCalendarMethods();
    }


    void noArgumentsLambdaExample() {

        //abstract runnable
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world one!");
            }
        };
        // Lambda Runnable
        Runnable r2 = () -> System.out.println("Hello world two!");

        r1.run();
        r2.run();

    }

    void argumentsLambdaExample() {

        Integer first = 1;
        Integer second = 2;

        //abstract comparator
        Comparator<Integer> c1 = new Comparator<Integer>() {
            @Override
            public int compare(final Integer o1, final Integer o2) {
                return o1.compareTo(o2);
            }
        };

        Comparator<Integer> c2 = (Integer i1, Integer i2) -> {
            return i1.compareTo(i2);
        };

        FunctionalInterface fInt = someInteger -> {
            System.out.println("An integer: " + someInteger);
        };


        System.out.println("First compare: " + c1.compare(first, second));
        System.out.println("Second compare: " + c2.compare(first, second));
        fInt.oneAbstractMethod(132456789);
    }

    void functionClassesExample() {
        //function
        Function<Integer, String> intToString = first ->
                "Function - Formatted integer: " + first;

        intToString.apply(10);

        //predicate
        Predicate<Integer> greaterThanZero = p -> p > 0;
        System.out.println("Predicate - Greater than zero test:" + greaterThanZero.test(12));

        //Consumer
        Consumer<String> stringConsumer = str -> System.out.println("Consumer - printing: " + str);
        stringConsumer.accept("A String");

        //Supplier
        Supplier stringSupplier = () -> {
            return "String";
        };
        System.out.println("Supplier - " + stringSupplier.get());

        //Unary operator
        UnaryOperator<Integer> unaryMultiply = number -> number * number;
        System.out.println("Unary op: " + unaryMultiply.apply(2));

        //Binary operator
        BinaryOperator<Integer> binaryAddition = (num1, num2) -> {
            return num1 + num2;
        };
        System.out.println("Binary op: " + binaryAddition.apply(2, 3));
    }


    void lambdaCollectionsExample() {

        //for each
        Consumer<String> stringConsumer = str -> System.out.println("Consumer - printing: " + str);
        stringConsumer.accept("A String");
        aList.forEach(stringConsumer);

        //filter
        Predicate<String> longerThanZero = p -> p.length() > 0;
        System.out.println("Filtered stream size: " + aList.stream().filter(longerThanZero).count());

        //collect list back
        List<String> filteredList = aList.stream().filter(longerThanZero).collect(Collectors.toList());
        System.out.println("Filtered list: " + filteredList);

    }

    void methodReference() {

        Java8Test test = new Java8Test();

        //static method
        Collections.sort(aList, Java8Test::compareStringsEqualStatic);
        //instance method
        Collections.sort(aList, test::compareStringsEqualInstance);
        //arbitrary Object
        String[] stringArray = {"Barbara", "James", "Mary", "John",
                "Patricia", "Robert", "Michael", "Linda"};
        Arrays.sort(stringArray, String::compareToIgnoreCase);
    }

    public static int compareStringsEqualStatic(String a, String b) {
        if (a.equals(b)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Schedule(when = "later")
    @Schedule(when = "now")
    void multipleAnnotations() {
        //something here
        boolean isPresent = this.getClass().isAnnotationPresent(Schedule.class);
    }

    public int compareStringsEqualInstance(String a, String b) {
        return Java8Test.compareStringsEqualStatic(a, b);
    }

    //doesnt work?
    void typeAnnotationExample() {

        String someVariable = null;
        System.out.println(someVariable);
    }

    private void newCalendarMethods() {
        //clock
        Clock clock = Clock.systemDefaultZone();
        System.out.println("Many seconds: " + clock.millis());
        //date
        LocalDate date = LocalDate.now();
        System.out.println("Current date is:" + date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth());
        LocalDate january = LocalDate.of(2015, Month.JANUARY, 1);
        //period
        Period period = Period.ofWeeks(2);
        period.plusDays(1);
        System.out.println("Period of " + period.getDays() + " days");
        date.plus(period);
        //local date time
        LocalDateTime dateTime = LocalDateTime.now();
        //local time
        LocalTime time = LocalTime.MAX;
        System.out.println("Start of day: " + LocalTime.MIN);
        System.out.println("Almost End of day: " + time.minus(1, ChronoUnit.SECONDS));
        //Duration
        Duration duration = Duration.between(dateTime, dateTime.plusDays(1));
        System.out.println("Duration of " + duration.getSeconds() + " seconds");
        //Instant
        Instant instant = Instant.now();
        System.out.println("Instant " + instant);
        //formater
        System.out.println("Formatted long now: " + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        System.out.println("Formatted short time now: " + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        System.out.println("Formatted pattern time now: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE HH:mm:ss")));
        //Calendar to Instant
        Instant legacyInstant = Calendar.getInstance().toInstant();
        //Date API to Legacy classes
        Date dt = Date.from(Instant.now());
        System.out.println(dt);

    }




}
