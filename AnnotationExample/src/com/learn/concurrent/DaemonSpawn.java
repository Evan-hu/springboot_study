package com.learn.concurrent;

/**
 * Created by Evan on 2017/10/19.
 */
public class DaemonSpawn implements Runnable {

    @Override
    public void run() {
        while (true) {
            Thread.yield();
        }
    }
}
