package com.learn.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evan on 2017/12/11.
 */
class CheckOutTask<T> implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;

    public CheckOutTask(Pool<T> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            T item = pool.checkOut();
            System.out.println(this + " check out " + item);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(this + " check in " + item);
            pool.checkIn(item);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "CheckOutTask " + id + " ";
    }
}


public class SemaphoreDemo {
    final static int SIZE = 25;

    public static void main(String[] args) throws InterruptedException {
        final Pool<Fat> pool = new Pool<>(Fat.class, SIZE);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i=0; i<SIZE; i++)
            exec.execute(new CheckOutTask<Fat>(pool));
        System.out.println("All CheckoutTask created");
        List<Fat> list = new ArrayList<>();
        for (int i=0; i<SIZE; i++) {
            Fat f = pool.checkOut();
            System.out.println(i+ " : main() thread checked out ");
            f.operation();
            list.add(f);
        }
        Future<?> blocked = exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    pool.checkOut();
                } catch (InterruptedException e) {
                    System.out.println("Checkout() Interrupted");
                }
            }
        });

        TimeUnit.SECONDS.sleep(2);
        blocked.cancel(true);
        System.out.println("Checking in object in " + list);
        for (Fat f: list) {
            System.out.println(f);
            pool.checkIn(f);
        }
        for (Fat f: list) {
            System.out.println(f);
            pool.checkIn(f);
        }
        exec.shutdown();
    }
}
