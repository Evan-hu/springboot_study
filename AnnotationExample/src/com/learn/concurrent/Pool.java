package com.learn.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Evan on 2017/12/11.
 * 对象池
 */
public class Pool<T> {
    private int size;
    private List<T> items = new ArrayList<T>();
    private volatile boolean [] checkedOut;
    private Semaphore available;

    public Pool(Class<T> classObject, int size) {
        this.size = size;
        checkedOut = new boolean[size];
        available = new Semaphore(size, true);
        for (int i=0; i<size; i++) {
            try {
                items.add(classObject.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public T checkOut() throws InterruptedException {
        available.acquire();
        return getItem();
    }

    public void checkIn(T x) {
        if (releaseItem(x))
            available.release();
    }

    public synchronized boolean releaseItem(T x) {
        int index = items.indexOf(x);
        if (-1 == index)
            return false;
        if (checkedOut[index]) {
            checkedOut[index] = false;
            return true;
        }
        return false;
    }

    private synchronized T getItem() {
        for (int i=0; i<size; i++) {
            if (!checkedOut[i]) {
                checkedOut[i] = true;
                return items.get(i);
            }
        }
        return null;
    }

}
