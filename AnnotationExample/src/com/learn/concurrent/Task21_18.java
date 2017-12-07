package com.learn.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evan on 2017/12/7.
 */
public class Task21_18 implements Runnable {
    private NonTask nonTask;

    public Task21_18(NonTask nonTask) {
        this.nonTask = nonTask;
    }

    @Override
    public void run() {
        System.out.println("Run longTime()");
        nonTask.longTime();
    }

    public static void main(String[] args) {
        Task21_18 t = new Task21_18(new NonTask());
        Thread thread = new Thread(t);
        thread.start();
        thread.interrupt();
    }
}

class NonTask {
    public void longTime() {
        System.out.println("longTime() start!");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            System.out.println("longTime() method Interrupted");
        }
        System.out.println("longTime() end!");
    }
}
