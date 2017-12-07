package com.example.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Evan on 2017/11/24.
 */
public class ParseData extends RecursiveTask<List<Map<String, Object>>> {
    private static final int THRESHOLD = 5000; //设置5000的阀值
    private int start;
    private int end;
    private ResultSet rs;
    private List<String> columnLabels;

    public ParseData(int start, int end, ResultSet rs, List<String> columnLabels) {
        this.start = start;
        this.end = end;
        this.rs = rs;
        this.columnLabels = columnLabels;
    }

    @Override
    protected List<Map<String, Object>> compute() {
        List<Map<String, Object>> result = new ArrayList<>();
        boolean canParse = (end - start) <= THRESHOLD;
        if (canParse) {
            try {
                while(rs.next()){
                    Map<String, Object> map = new HashMap<String, Object>();
                    for (String columnLabel : columnLabels) {
                        Object value = rs.getObject(columnLabel);
                        map.put(columnLabel.toLowerCase(), value);
                    }
                    result.add(map);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            int middle = (start + end) / 2;
            ParseData leftParse = new ParseData(start, middle, null, columnLabels);
        }
        return null;
    }
}
