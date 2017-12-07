package com.learn.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by Evan on 2017/11/15.
 */

class Car {
    private boolean waxon = false;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public synchronized void waxed() {
       /* System.out.println("wax over, waiting for buff");
        waxon = true; // ready to buff
        notifyAll();*/
       lock.lock();
       try {
           waxon = true;
           condition.signalAll();
       } finally {
           lock.unlock();
       }

    }

    public synchronized void buffed() {
        /*System.out.println("buff over, waiting for wax");
        waxon = false; // ready for another coat of wax
        notifyAll();*/
        lock.lock();
        try {
            waxon = false;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public synchronized void waitForWaxing() throws InterruptedException {
        while (waxon == false)
            wait();
    }

    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxon == true)
            wait();
    }
}

class WaxOn implements Runnable {
    private Car car;
    public WaxOn(Car car) {
        this.car = car;
    }
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            System.out.println("Wax on!");
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
                car.waxed();
                car.waitForBuffing();
            } catch (InterruptedException e) {
                System.out.println("Exiting via interrupt");
            }
            System.out.println("Ending Wax On task");
        }
    }
}


class WaxOff implements Runnable {
    private Car car;
    public WaxOff(Car c) {
        this.car = c;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
              car.waitForWaxing();
              System.out.println("Wax Off!");
              TimeUnit.MILLISECONDS.sleep(2000);
              car.buffed();
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending Wax Off task");
    }
}


public class WaxoMatic {
    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new WaxOff(car));
        exec.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5); // run for a while...
        exec.shutdown();
    }

}
