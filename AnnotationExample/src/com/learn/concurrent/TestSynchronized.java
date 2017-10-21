package com.learn.concurrent;

/**
 * Created by Evan on 2017/10/21.
 */
public class TestSynchronized {

    public synchronized void s1Method() {
        System.out.println("This is S1 method");
    }

    public synchronized void s2Method() {
        System.out.println("This is S2 method");
    }

    public static void main(String[] args) {
        TestSynchronized t = new TestSynchronized();
        Thread t1 = new Thread(new S1(t));
        Thread t2 = new Thread(new S1(t));
        t1.start();
        t2.start();
    }

}

class S1 implements Runnable {

    private TestSynchronized t;

    public S1(TestSynchronized t) {
        this.t = t;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread sleep 15s, ThreadName: " + Thread.currentThread().getName());
            Thread.sleep(15000);
            t.s1Method();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class S2 implements Runnable {

    private TestSynchronized t;

    public S2(TestSynchronized t) {
        this.t = t;
    }

    @Override
    public void run() {
        System.out.println("this is S2, let's test start");
        t.s1Method();
    }
}