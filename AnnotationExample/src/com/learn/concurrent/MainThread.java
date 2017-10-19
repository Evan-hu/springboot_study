package com.learn.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Evan on 2017/10/16.
 */
public class MainThread {
    public static void main1(String[] args) {
        LiftOff launch = new LiftOff();
        launch.run();
    }

    // basicThread
    public static void main2(String[] args) {
        Thread t = new Thread(new LiftOff());
        t.start();
        System.out.println("Waiting for Liftoff");
    }

    // MoreBasicThread
    public static void main3(String[] args) {
        for (int i=0; i<5; i++) {
            new Thread(new LiftOff()).start();
            System.out.println("Waiting for Liftoff:" + i);
        }

    }

    // use Executer
    public static void main4(String[] args) {
        ExecutorService e = Executors.newCachedThreadPool();
        for (int i=0; i<5; i++) {
            e.execute(new LiftOff());
        }
        e.shutdown();
        System.out.println("The main method is over!");
    }

    // use SingleThreadPool
    public static void main(String[] args) {
        ExecutorService e = Executors.newSingleThreadExecutor();
        for (int i=0; i<5; i++) {
            e.execute(new LiftOff());
        }
        e.shutdown();
        System.out.println("The main method is over!");
    }
}
