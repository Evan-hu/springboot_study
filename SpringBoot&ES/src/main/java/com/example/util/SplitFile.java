package com.example.util;

import java.io.*;
import java.util.Date;

/**
 * Created by Evan on 2017/12/1.
 */
public class SplitFile {
    public static void main(String[] args) {
        split("C:\\Users\\Evan\\Desktop\\es\\data_10.json");
    }

    public static void split(String fileName) {
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName));
            BufferedReader br = new BufferedReader(reader);
            BufferedWriter bw = null;
            String str = "";
            String tempData = br.readLine();
            int i=1, s=0;
            System.out.println(new Date());
            while (tempData != null) {
//                str += tempData+"\n";
//                if (i%100000 == 0) {
//                    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\data_"+s+".json")));
//                    bw.write(str);
//                    bw.close();
//                    str = "";
//                    s += 1;
//                    System.out.println("write over "+s+" file!");
//                    System.out.println(new Date());
//                }
                i++;
                tempData = br.readLine();
            }
//            if ((i-1)%100000 != 0) {
//                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\data_"+s+".json")));
//                bw.write(str);
//                bw.close();
//                str = "";
//                s += 1;
//                System.out.println("write over one file!");
//            }
            reader.close();
            System.out.println("i" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
