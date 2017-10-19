package com.test.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Evan on 2017/10/13.
 *
 *
 */
public class UseCaseTracker {

    public static void trackUseCases(List<Integer> useCases, Class<?> cl) {
        for (Method m : cl.getDeclaredMethods()) {
            UseCase uc = m.getAnnotation(UseCase.class);
            if (uc != null) {
                System.out.println("Found UseCase:" + uc.id() + " " + uc.description());
                useCases.remove(new Integer(uc.id()));
            }
        }
        for (int i : useCases) {
            System.out.println("Warning: Missing use cases-"+i);
        }
    }

    public static void main(String[] args) {
//        List<Integer> useCases = new ArrayList<>();
//        Collections.addAll(useCases, 47, 48, 49, 50);
//        trackUseCases(useCases, PasswordUtils.class);
        System.out.println((32 >>> 28) & 15);
        System.out.println(Integer.parseInt("00011111", 2) & 15);
        System.out.println(Integer.parseInt("00111111", 2) & 15);
        System.out.println(Integer.parseInt("01111111", 2) & 15);
        System.out.println(Integer.parseInt("11111111", 2) & 15);
        System.out.println(Integer.parseInt(toFullBinaryString(Integer.parseInt("00011111", 2) &15), 2) &15);
        System.out.println(Integer.parseInt(toFullBinaryString(Integer.parseInt("00111111", 2) &15), 2) &15);
        System.out.println(Integer.parseInt(toFullBinaryString(Integer.parseInt("01111111", 2)&15), 2) &15);
        System.out.println(Integer.parseInt(toFullBinaryString(Integer.parseInt("11111111", 2)&15), 2) &15);
    }

    public static String toFullBinaryString(int num) {
        char[] chs = new char[Integer.SIZE];
        for(int i = 0; i < Integer.SIZE; i++) {
            chs[Integer.SIZE - 1 - i] = (char)(((num >> i) & 1) + '0');
        }
        String chars = new String(chs);
        System.out.println(chars);
        return chars;
    }

}
