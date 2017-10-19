package com.test.annotation;

/**
 * Created by Evan on 2017/10/9.
 */
public class AnnotationExample {
    public static void main(String[] args) {

    }

    @Override
    @MethodInfo(author = "scan", comments = "just a test", date = "now")
    public String toString() {
        return "Override toString method";
    }
}
