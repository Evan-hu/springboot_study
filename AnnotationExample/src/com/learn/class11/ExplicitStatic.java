package com.learn.class11;

/**
 * Created by Evan on 2017/12/14.
 */
class Cup {
    static int i = 0;
    static {
        System.out.println("static {}");
    }
    Cup(int marker) {
        System.out.println(i);
        System.out.println("Cup("+marker+")");
    }
    void f(int marker) {
        System.out.println("f("+marker+")");
    }
}

class Cups {
    static Cup cup1;
    static Cup cup2;
    static {
        cup1 = new Cup(1);
        cup2 = new Cup(2);
    }
}

public class ExplicitStatic {
    public static void main(String[] args) {
        System.out.println("Inside Main()");
        Cups.cup1.f(99);
        String test = "a";
        System.out.println("hashCode(): "+test.hashCode());
    }
}
