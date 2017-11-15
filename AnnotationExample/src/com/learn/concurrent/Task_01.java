package com.learn.concurrent;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evan on 2017/11/15.
 */
class Flag {
    public synchronized void notifies() {
        notifyAll();
    }
    public synchronized void w() throws InterruptedException {
        wait();
    }
}
class First implements Runnable {
    private Flag flag;
    public First(Flag f) {
        this.flag = f;
    }
    @Override
    public void run() {
        try {
            System.out.println("Waiting Status");
            flag.w();
            System.out.println("notify me to do sth");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Second implements  Runnable {
    private Flag flag;
    public Second(Flag f) {
        this.flag = f;
    }
    @Override
    public void run() {
        System.out.println("waiting 5 seconds");
        try {
            TimeUnit.SECONDS.sleep(5);
            flag.notifies();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


public class Task_01 {
    public static void main(String[] args) {
        Flag flag = new Flag();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new First(flag));
        exec.execute(new Second(flag));
        exec.shutdown();
    }
}
