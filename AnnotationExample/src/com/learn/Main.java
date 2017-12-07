package com.learn;

import com.learn.es.JDBCUtil;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;

/**
 * Created by Evan on 2017/11/21.
 */
public class Main {
    public static void main(String[] args) {
        try {
            JDBCUtil.readDataByPage("TASKCUSTOMERREF",1000,"D://data.json","index","type");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
