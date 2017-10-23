package com.learn.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Evan on 2017/10/21.
 */
public class AtomicIntegerTest implements Runnable {
    private AtomicInteger i = new AtomicInteger(0);
    public int getValue() {
        return i.get();
    }

    private void evenIncrement() {
        i.addAndGet(2);
    }

    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {


    }
}
