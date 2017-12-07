package com.learn.concurrent;

import com.sun.javafx.tk.quantum.GlassSceneDnDEventHandler;
import sun.util.resources.CurrencyNames_es_ES;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evan on 2017/12/7.
 */

class NeedCleanup {
    private final int id;
    public NeedCleanup(int id) {
        this.id = id;
        System.out.println("needsCleanup " + id);
    }
    public void cleanup() {
        System.out.println("clean up " + id);
    }
}

class Blocked3 implements Runnable {
    private volatile double d = 0.0;

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                NeedCleanup n1 = new NeedCleanup(1);
                try {
                    System.out.println("Sleeping");
                    TimeUnit.SECONDS.sleep(1);
                    NeedCleanup n2 = new NeedCleanup(2);
                    try {
                        System.out.println("Calculating");
                        for (int i=1; i<25000; i++)
                            d = d+(Math.PI+Math.E)/d;
                        System.out.println("Finish time-consuming operation");
                    } finally {
                        n2.cleanup();
                    }
                } finally {
                    n1.cleanup();
                }
            }
            System.out.println("Exiting via while() test");
        } catch (InterruptedException e) {
            System.out.println("Exiting via InterruptedException");
        }
    }
}


public class InterruptingIdiom {
    public static void main(String[] args) throws InterruptedException {
       Thread t = new Thread(new Blocked3());
       t.start();
       TimeUnit.SECONDS.sleep(new Integer("1"));
       t.interrupt();
    }
}
