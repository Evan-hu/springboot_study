package com.learn.concurrent;

import java.util.concurrent.Callable;

/**
 * Created by Evan on 2017/10/18.
 */
public class TaskWithResult implements Callable<String> {

    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        return "result of TaskWithResult " + id;
    }
}
