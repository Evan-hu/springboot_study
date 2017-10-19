package com.learn.concurrent;

/**
 * Created by Evan on 2017/10/19.
 */
public class DifNewThread {
}

// method 1 Using a named inner class
class InnerThread1 {
    private int countDown = 5;
    private Inner inner;
    // eg create a Thread
    private class Inner extends Thread {
        Inner(String name) {
            super(name);
            start();
        }

        public void run() {
            while (true) {
                System.out.println(this);
                if (--countDown == 0) return;
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public String toString() {
            return getName()+": " + countDown;
        }
    }

    public InnerThread1 (String name) {
        inner = new Inner(name);
    }

}

// using an anonymous inner class
class InnerThread2 {
    private int countDown = 5;
    private Thread t;
    public InnerThread2 (String name) {
        t = new Thread(name) {
          public void run() {

          }
          public String toString() {
              return "";
          }
        };
        t.start();
    }
}


class InnRunnable1 {
    private int countDown = 5;
    private Inner inner;
    private class Inner implements Runnable{
        Thread t;
        Inner(String name) {
            t = new Thread(this, name);
            t.start();
        }
        @Override
        public void run() {

        }

        public String toString() {
            return "";
        }
    }
}
