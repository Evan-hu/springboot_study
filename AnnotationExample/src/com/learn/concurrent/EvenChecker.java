package com.learn.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Evan on 2017/10/20.
 */
public class EvenChecker implements Runnable {
    private IntGenerator generator;
    private final int id;
    public EvenChecker(IntGenerator generator, int ident) {
        this.generator = generator;
        id = ident;
    }

    @Override
    public void run() {
        while (!generator.isCanceled()) {
            int val = generator.next();
            System.out.println("val: " + val);
            if (val % 2 != 0) {
                System.out.println(val +" not even!");
                generator.cancel();
            }
        }
    }

    public static void test(IntGenerator g, int count) {
        System.out.println("Press Control-c to exit");
        ExecutorService e = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            e.execute(new EvenChecker(g, i));
        }
        e.shutdown();
    }

    public static void test(IntGenerator g) {
        test(g, 10);
    }

}
