package cz.matousekd.java8test;

import java.lang.annotation.Repeatable;

/**
 * @author David.Matousek
 *
 */
@Repeatable(value = Schedules.class)
public @interface Schedule {

    String when() default "";
}
