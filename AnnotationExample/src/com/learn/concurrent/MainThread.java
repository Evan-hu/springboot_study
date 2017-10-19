package com.learn.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
    public static void main5(String[] args) {
        ExecutorService e = Executors.newSingleThreadExecutor();
        for (int i=0; i<5; i++) {
            e.execute(new LiftOff());
        }
        e.shutdown();
        System.out.println("The main method is over!");
    }

    // use Callable interface
    public static void main6(String[] args) {
        ExecutorService e = Executors.newCachedThreadPool();
        List<Future<String>> results = new ArrayList<Future<String>>();
        for (int i=0; i<10; i++)
            results.add(e.submit(new TaskWithResult(i)));
        for (Future<String> future: results) {
            try {
                System.out.println(future.get());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                e.shutdown();
            }
        }

    }


    // use daemons
    public static void main(String[] args) throws InterruptedException {
        for (int i=0; i<10; i++) {
            Thread daemon = new Thread(new SimpleDaemons());
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("All daemons started");
        TimeUnit.MICROSECONDS.sleep(175);
    }

}
