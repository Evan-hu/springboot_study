package com.learn.es;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Evan on 2017/11/21.
 */
public class JDBCUtil {
    private static Connection conn = null;
    private static PreparedStatement pst = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "root");
            System.out.println(conn.getClientInfo());
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
        File file = new File(path);
        int count = 0;
        int size = list.size();
        for(Map map : list){
            FileUtils.write(file,  "{ \"index\" : { \"_index\" : \""+index+"\", \"_type\" : \""+type+"\" } }\n","UTF-8",true);
            FileUtils.write(file, JSON.toJSONString(map)+"\n","UTF-8",true);
//            System.out.println("写入了" + ((count++)+1) + "[" + size + "]");
        }
        System.out.println("写入完成");
    }

    /**
     * 读取数据
     * @param sql
     * @return
     * @throws SQLException
     */
    public static List<Map> readTable(String tablename,int start,int end) throws SQLException {
        System.out.println("开始读数据库");
        //执行查询
        pst = conn.prepareStatement("select * from(select rownum as rn,t.* from "+tablename+" t )where rn >="+start+" and rn <"+end);
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
        for(i =0;i<count;){
            List<Map> map = JDBCUtil.readTable(tablename,i,i+page);
            JDBCUtil.writeTable(map,index,type,path);
            i+=page;
        }
    }
}
