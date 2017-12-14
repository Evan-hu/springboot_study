package com.learn.class11;

/**
 * Created by Evan on 2017/12/13.
 */
public class DotNew {
    public class Inner {}

    public static void main(String[] args) {
        DotNew dn = new DotNew();
        DotNew.Inner d1 = dn.new Inner();
    }
}
