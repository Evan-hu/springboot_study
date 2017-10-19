package com.learn.concurrent;

/**
 * Created by Evan on 2017/10/16.
 */
public class LiftOff implements Runnable {

    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;
    public LiftOff() {
        System.out.println("Thread is starting !");
    }
    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#"+id+"("+(countDown > 0 ? countDown : "Liftoff!") + ")." + Thread.currentThread().getName();
    }

    @Override
    public void run() {
//        while (countDown-- > 0) {
//            System.out.println(status());
//            Thread.yield();
//        }
        for (int i=0; i<3; i++) {
            System.out.println(status());
            System.out.println("Priority: " + Thread.currentThread().getPriority());
            Thread.yield();
        }
    }
}
