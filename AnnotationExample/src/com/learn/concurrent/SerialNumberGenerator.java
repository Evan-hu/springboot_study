package com.learn.concurrent;

/**
 * Created by Evan on 2017/10/21.
 */
public class SerialNumberGenerator {
    private static volatile int serialNumber = 0;
    public static int nextSerialNumber() {
        return serialNumber++;
    }
}
