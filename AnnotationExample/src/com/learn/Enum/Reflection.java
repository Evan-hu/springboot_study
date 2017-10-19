package com.learn.Enum;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Evan on 2017/10/12.
 */
enum Explore {HERE, THERE}

public class Reflection {
    public static Set<String> analyze(Class<?> enumClass) {
        System.out.println("---------"+"analyze " + enumClass + "---------");
        System.out.println("Interface:");
        for (Type t: enumClass.getGenericInterfaces()) {
            System.out.println(t);
        }
        System.out.println("Method:");
        Set<String> methods = new TreeSet<String>();
        for (Method m: enumClass.getMethods()) {
            System.out.println(m.getName());
        }
        System.out.println(methods);
        return methods;
    }

    public static void main(String[] args) {
        Set<String> exploreMethods = analyze(Explore.class);
        Set<String> enumMethods = analyze(Enum.class);
        System.out.println("Explore.containsAll(Enum)?"+exploreMethods.containsAll(enumMethods));
    }
}
