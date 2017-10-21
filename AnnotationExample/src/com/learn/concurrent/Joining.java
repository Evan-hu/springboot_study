package com.learn.concurrent;

/**
 * Created by Evan on 2017/10/20.
 */
public class Joining {

    public static void main(String[] args) {
        Sleeper
                sleepy = new Sleeper("sleepy", 5000),
                grumpy = new Sleeper("grumpy", 1500);
        Joiner
                dopey = new Joiner("dopey", sleepy),
                doc = new Joiner("doc", grumpy);
        grumpy.interrupt();
    }

}

class Sleeper extends Thread {
    private int duration;

    public Sleeper(String name, int sleepTime) {
        super(name);
        this.duration = sleepTime;
        start();
        System.out.println(name + " is running");
    }

    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted." + "isInterrupted(): " + isInterrupted());
            return;
        }
        System.out.println(getName() + " has awakened");
    }
}

class Joiner extends Thread {
    private Sleeper sleeper;

    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    public void run() {
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println(getName() + " join completed");
    }
}
