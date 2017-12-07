package com.example.util;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Evan on 2017/11/21.
 */
public class Main {
    public static void main(String[] args) {
        /*try {
            JDBCUtil.readDataByPage("TASKCUSTOMER",100000,"D://data_","task_base","task_base_list");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        int count = Integer.SIZE - 3;

        System.out.println(-1 << count);
        System.out.println(0 << count);
        System.out.println(1 << count);
        System.out.println(2 << count);
        System.out.println(3 << count);
        System.out.println(1 &~ ((1 << count) - 1));
    }
}
