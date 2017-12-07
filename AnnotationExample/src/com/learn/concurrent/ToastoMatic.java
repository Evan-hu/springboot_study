package com.learn.concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Evan on 2017/11/16.
 */
class Toast {
    public enum Status {
        DRY, BUFFERED, JAMMED
    }
    private Status status = Status.DRY;
    private final int id;
    public Toast(int idn) {
        this.id = idn;
    }
    public void buffer() {
        status = status.BUFFERED;
    }
    public void jam() {
        status = status.JAMMED;
    }
    public Status getStatus() {
        return status;
    }
    public int getId() {
        return id;
    }
    public String toString() {
        return "Toast " + id + ": " + status;
    }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {}

/**
 *  Make Toast
 */
class Toaster implements Runnable {
    private ToastQueue toastQueue;
    private int count = 0;
    private Random rand = new Random(47);
    public Toaster(ToastQueue queue) {
        toastQueue = queue;
    }
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100+rand.nextInt(500));
                Toast t = new Toast(count++);
                System.out.println(t);
                toastQueue.put(t);
            } catch (InterruptedException e) {
                System.out.println("Toaster interrupted");
            }
            System.out.println("Toaster Off");
        }
    }
}

class Bufferer implements Runnable {
    private ToastQueue dryQueue, bufferedQueue;
    public Bufferer(ToastQueue dry, ToastQueue buffered) {
        this.dryQueue = dry;
        this.bufferedQueue = buffered;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast t = dryQueue.take();
                t.buffer();
                System.out.println(t);
                bufferedQueue.put(t);
            }
        } catch (InterruptedException e) {
            System.out.println("Bufferer interrupted");
        }
        System.out.println("Bufferer Off");
    }
}

class Jammer implements Runnable {
    private ToastQueue bufferedQueue, finishedQueue;
    public Jammer(ToastQueue buffered, ToastQueue finished) {
        this.bufferedQueue = buffered;
        this.finishedQueue = finished;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast t = bufferedQueue.take();
                t.jam();
                System.out.println(t);
                finishedQueue.put(t);
            }
        } catch (InterruptedException e) {
            System.out.println("Jammer interrupted");
        }
        System.out.println("Jammer Off");
    }
}

class Eater implements Runnable {
    private ToastQueue finishedQueue;
    private int counter = 0;
    public Eater(ToastQueue finished) {
        this.finishedQueue = finished;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast t = finishedQueue.take();
                if (t.getId() != counter++ || t.getStatus()!= Toast.Status.JAMMED) {
                    System.out.println(">>> Error: " + t);
                    System.exit(1);
                } else {
                    System.out.println("Chomp! " + t);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Eater interrupted");
        }
        System.out.println("Eater Off");
    }
}


public class ToastoMatic {
    public static void main(String[] args) throws InterruptedException {
        ToastQueue dryQueue = new ToastQueue(),
                bufferedQueue = new ToastQueue(),
                finishedQueue = new ToastQueue();
        System.out.println("dryQueue: "+dryQueue);
        System.out.println("bufferedQueue: "+bufferedQueue);
        System.out.println("finishedQueue: "+finishedQueue);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Toaster(dryQueue));
        exec.execute(new Bufferer(dryQueue, bufferedQueue));
        exec.execute(new Jammer(bufferedQueue, finishedQueue));
        exec.execute(new Eater(finishedQueue));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }

}
