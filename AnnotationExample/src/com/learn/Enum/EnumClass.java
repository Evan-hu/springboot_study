package com.learn.Enum;

import java.io.UnsupportedEncodingException;
/**
 * Created by Evan on 2017/10/10.
 */
//enum tset {GREED, YELLOW, RED}

public class EnumClass {
    Color color = Color.RED;

    public void change() {
        switch (color) {
            case RED: color = Color.GREED;
                break;
            case GREED: color = Color.YELLOW;
                break;
            case YELLOW: color = Color.RED;
                break;
        }
    }

    public String toString() {
        return "the color is " + color;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        EnumClass e = new EnumClass();
        for (int i=0; i<7; i++) {
            System.out.println(e);
            e.change();
        }
    }
}
