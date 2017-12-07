package com.example.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.scanner.ScannerImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by Evan on 2017/11/21.
 */
public class JDBCUtil {
    private static Connection conn = null;
    private static PreparedStatement pst = null;

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@221.236.20.222:15218/ORCL", "sms", "Ph0sms");
        } catch (Exception e) {
            System.out.println("Connection sql exception");
        }
        System.out.println("Connection sql over");
    }


    /**
     * 把查到的数据格式化写入到文件
     *
     * @param list 需要存储的数据
     * @param index 索引的名称
     * @param type 类型的名称
     * @param path 文件存储的路径
     **/
    public static void writeTable(List<Map> list,String index,String type,String path) throws SQLException, IOException {
        System.out.println("开始写文件");
//        File file = new File(path+".json");
        FileWriter fw = new FileWriter(path+".json");
        int count = 0;
        int size = list.size();
        for(Map map : list){
//            FileUtils.write(file,  "{ \"index\" : { \"_index\" : \""+index+"\", \"_type\" : \""+type+"\" } }\n","UTF-8",true);
//            FileUtils.write(file, JSON.toJSONString(map)+"\n","UTF-8",true);
//            System.out.println("写入了" + ((count++)+1) + "[" + size + "]");
            fw.write("{ \"index\" : { \"_index\" : \""+index+"\", \"_type\" : \""+type+"\" } }\n");
            fw.write(JSON.toJSONString(map)+"\n");
        }
        fw.flush();
        fw.close();
        System.out.println("写入完成");
    }

    /**
     * 读取数据
     * @param
     * @return
     * @throws SQLException
     */
    public static List<Map> readTable(String tablename,int start,int end) throws SQLException {
        System.out.println("开始读数据库");
        //执行查询
        pst = conn.prepareStatement("select s.* from (select rownum as rn,t.* from "+tablename+" t ) s where s.rn >="+start+" and s.rn <"+end);
        ResultSet rs = pst.executeQuery();

        //获取数据列表
        List<Map> data = new ArrayList();
        List<String> columnLabels = getColumnLabels(rs);

        Map<String, Object> map = null;
        while(rs.next()){
            map = new HashMap<String, Object>();
            for (String columnLabel : columnLabels) {
                Object value = rs.getObject(columnLabel);
                map.put(columnLabel.toLowerCase(), value);
            }
            data.add(map);
        }
        pst.close();
        System.out.println("数据读取完毕");
        return data;
    }
    /**
     * 获得列名
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private static List<String> getColumnLabels(ResultSet resultSet)
            throws SQLException {
        List<String> labels = new ArrayList<String>();

        ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            labels.add(rsmd.getColumnLabel(i + 1));
        }

        return labels;
    }
    /**
     * 获得数据库表的总数，方便进行分页
     *
     * @param tablename 表名
     */
    public static int count(String tablename) throws SQLException {
        int count = 0;
        System.out.println(conn.isClosed());
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select count(1) from "+tablename);
        while (rs.next()) {
            count = rs.getInt(1);
        }
        System.out.println("Total Size = " + count);
        rs.close();
        stmt.close();
        return count;
    }
    /**
     * 执行查询，并持久化文件
     *
     * @param tablename 导出的表明
     * @param page 分页的大小
     * @param path 文件的路径
     * @param index 索引的名称
     * @param type 类型的名称
     * @return
     * @throws SQLException
     */
    public static void readDataByPage(String tablename,int page,String path,String index,String type) throws SQLException, IOException {
        int count = count(tablename);
        int i =0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tag = "";
        System.out.println((count/page+1));
        for(i =0;i<(count/page+1);){
            System.out.println("Batch Number：" + i);
            tag = sdf.format(new Date());
            List<Map> map = JDBCUtil.readTable(tablename,i,i+page);
            JDBCUtil.writeTable(map,index,type,path+tag);
            i+=page;
        }
    }
}
