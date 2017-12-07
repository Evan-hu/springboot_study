package com.learn.concurrent;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Evan on 2017/12/7.
 */
public class DeadlockingDiningPhilosophers {
    public static void main(String[] args) throws IOException {
        int ponder = 5;
        int size = 5;
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick [] chopsticks = new Chopstick[size];
        for (int i=0; i<size; i++)
            chopsticks[i] = new Chopstick();
        for (int i=0; i<ponder; i++)
            exec.execute(new Philosipher(
                    chopsticks[i], chopsticks[(i+1)%size],i, ponder
            ));
        System.out.println("" +"enter to quit");
        System.in.read();
        exec.shutdownNow();
    }
}
