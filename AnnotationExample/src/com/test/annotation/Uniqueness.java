package com.test.annotation;

/**
 * Created by Evan on 2017/10/13.
 */
public @interface Uniqueness {
    Constraints constraints() default @Constraints(unique = true);
}
