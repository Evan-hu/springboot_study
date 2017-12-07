package com.learn.concurrent;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Evan on 2017/12/7.
 */

class Sender implements Runnable {

    private Random random = new Random(47);
    private PipedWriter out = new PipedWriter();
    private BlockingQueue blockingQueue = new LinkedBlockingDeque();
    public PipedWriter getPipedWriter() {
        return out;
    }

    public BlockingQueue getBlockingQueue() {
        return blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            for (char c='A'; c<= 'z'; c++) {
                System.out.println(c);
                try {
//                    out.write(c);
                    blockingQueue.put(c);
                    TimeUnit.MILLISECONDS.sleep(random.nextInt());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class Receiver implements Runnable {
    private PipedReader in;
    private BlockingQueue blockingQueue = new LinkedBlockingDeque();
    public Receiver(Sender sender) throws IOException {
        blockingQueue = sender.getBlockingQueue();
    }

    @Override
    public void run() {
        while (true) {
            try {
//                System.out.println("Read: " + in.read()+", ");
                System.out.println("Read blockingQueue " + blockingQueue.take());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


public class PipedIO {
    public static void main(String[] args) throws Exception {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(sender);
        exec.execute(receiver);
        TimeUnit.SECONDS.sleep(4);
        exec.shutdownNow();
    }
}
