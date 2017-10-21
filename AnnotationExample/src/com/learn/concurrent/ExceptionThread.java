package com.learn.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Evan on 2017/10/20.
 */
public class ExceptionThread implements Runnable{
    @Override
    public void run() {
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler());
        throw new RuntimeException("RuntimeException: ExceptionThread");
    }

    public static void main(String[] args) {
        ExecutorService e = Executors.newCachedThreadPool();
        try {
            e.execute(new ExceptionThread());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            e.shutdown();
        }
    }

}

class MyExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Thread name: " + t.getName() + "Exception: " + e.getMessage());
    }
}
