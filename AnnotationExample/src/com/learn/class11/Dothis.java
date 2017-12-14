package com.learn.class11;

/**
 * Created by Evan on 2017/12/12.
 */
public class Dothis {
    void f() {
        System.out.println("Dothis.f()");
    }

    public class Inner {
        public Dothis outer() {
            return Dothis.this;
        }
    }

    public Inner inner() {
        return new Inner();
    }

    public static void main(String[] args) {
        Dothis dt = new Dothis();
        Dothis.Inner dti = dt.inner();
        dti.outer().f();
    }
}
