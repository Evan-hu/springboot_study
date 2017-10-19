package com.learn.concurrent;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evan on 2017/10/19.
 */
public class Daemons {
    public static void main(String[] args) {
        System.out.println(Math.random()*1000);
        Thread d = new Thread(new Daemon());
        d.setDaemon(true);
        d.start();
        System.out.println("d.isDaemon() = "+ d.isDaemon()+". ");
        try {
            // there will be deadlock
            TimeUnit.SECONDS.sleep(new Random().nextInt());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Daemon implements Runnable {
    private Thread  [] t = new Thread[10];
    @Override
    public void run() {
        for (int i=0; i<10; i++) {
            t[i] = new Thread(new DaemonSpawn());
            t[i].start();
            System.out.println("DaemonSapwn " + i + "started.");
        }
        for (int i=0; i<t.length; i++) {
            System.out.println("t["+i+"].isDarmon() = "+ t[i].isDaemon()+ ". ");
        }
        while (true)
            Thread.yield();
    }
}