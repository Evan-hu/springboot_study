package com.test.annotation;

import java.lang.annotation.*;

/**
 * Created by Evan on 2017/10/9.
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInfo {
    String author() default "Evan";
    String date();
    int revision() default 1;
    String comments();
}
