package com.learn.classload;

/**
 * Created by Evan on 2017/11/14.
 */
public class ClassLoad_01 {


    public static void main(String[] args) {
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
        System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());
    }
}
