package com.learn.concurrent;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Pair {
    private int x, y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Pair() {
        this(0,0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void incrementX() {
        x++;
    }

    public void incrementY() {
        y++;
    }

    public String toString() {
        return "x: " + x+ "y: " + y;
    }

    public class PairValuesNotEqualException extends RuntimeException {
        public PairValuesNotEqualException() {
            super("Pair values not equal: " + Pair.this);
        }
    }

    public void checkState() {
        if (x != y) {
            throw new PairValuesNotEqualException();
        }
    }
}

abstract class PairManager {
    AtomicInteger checkCount = new AtomicInteger(0);
    protected Pair p = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());

    public synchronized Pair getPair() {
        return new Pair(p.getX(), p.getY());
    }

    public void store(Pair p) {
        storage.add(p);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void increment();
}

//  method 加上同步
class PairManager1 extends PairManager {
    @Override
    public synchronized void increment() {
        p.incrementX();
        p.incrementY();
        store(getPair());
    }
}

// method 内部加上同步
class PairManager2 extends PairManager {
    @Override
    public void increment() {
        Pair temp;
        synchronized (this) {
            p.incrementX();
            p.incrementY();
            temp = getPair();
        }
        store(temp);
    }
}


class PairManipulator implements Runnable {
    private PairManager pm;
    public PairManipulator(PairManager pm) {
        this.pm = pm;
    }

    @Override
    public void run() {
        while (true) {
            pm.increment();
        }
    }

    public String toString() {
        return "Pair: " + pm.getPair() + " checkCounter= " + pm.checkCount.get();
    }
}


class PairChecker implements Runnable {
    private PairManager pm;
    public PairChecker(PairManager pm) {
        this.pm = pm;
    }

    @Override
    public void run() {
        while (true) {
            pm.checkCount.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}

/**
 * Created by Evan on 2017/10/23.
 */
public class CriticalSection {

    static void testApproaches(PairManager pm1, PairManager pm2) {
        ExecutorService e = Executors.newCachedThreadPool();
        PairManipulator pmpn1 = new PairManipulator(pm1),
                pmpn2 = new PairManipulator(pm2);
        PairChecker pc1 = new PairChecker(pm1),
                pc2 = new PairChecker(pm2);

        e.execute(pmpn1);
        e.execute(pmpn2);
        e.execute(pc1);
        e.execute(pc2);

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e1) {
            System.out.println("Sleep interrupted");
        }

        System.out.println("pmpn1: " + pmpn1 + " \npmpn2: " + pmpn2);
        System.exit(0);
    }

    public static void main(String[] args) {
        PairManager pman1 = new PairManager1();
        PairManager pman2 = new PairManager2();
        testApproaches(pman1, pman2);
    }

}
