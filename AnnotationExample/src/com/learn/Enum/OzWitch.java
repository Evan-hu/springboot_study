package com.learn.Enum;

/**
 * Created by Evan on 2017/10/12.
 */
public enum OzWitch {
    WEST("test west"),
    NORTH("test north"),
    SOUTH("test south"),
    EAST("test east");

    private String desc;
    private OzWitch(String desc) {
        this.desc = desc;
    }
    private String getDesc() {
        return desc;
    }

    public static void main(String[] args) {
        for (OzWitch ozWitch: OzWitch.values()) {
            System.out.println(ozWitch + ": "+ ozWitch.getDesc());
        }
    }
}
