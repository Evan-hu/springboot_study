package com.learn.Enum;

/**
 * Created by Evan on 2017/10/12.
 */
public class PostOffice {
}


class Mail {
    enum GeneralDelivery {YES, NO1, No2, NO3, NO4, NO5}
    enum Scannability {UNSCANNABLE, YES1, YES2, YES3, YSE4}
    enum Readability {ILLEGIBLE, YES1, YSE2, YES3, YES4}
    enum Address {INCORRECT, OK1, OK2, OK3, OK4, OK5, OK6}
    enum ReturnAddress {MISSING, OK1, OK2, OK3, OK4, OK5}
    GeneralDelivery generalDelivery;
    Scannability scannability;
    Readability readability;
    Address address;
    ReturnAddress returnAddress;
    static long counter = 0;
    long id = counter++;
    public String toString() {
        return "Mail " + id;
    }
    public String details() {
        return toString() +
                ". General Delivery:" + generalDelivery +
                ". Address Scanability: " + scannability +
                ". Address Readability: " + readability +
                ". Address Address: " + address +
                ". Return address: " + readability;
    }

    public static Mail randomMail() {
        Mail m = new Mail();
        return m;
//        m.generalDelivery = Enums.random(GeneralDelivery.class);
    }
}
