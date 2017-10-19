package com.test.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2017/10/13.
 */
public class TableCreator {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("arguments: annotated classes");
            System.exit(0);
        }
        for (String className : args) {
            Class<?> cl = Class.forName(className);
            DBTable dbTable = cl.getAnnotation(DBTable.class);
            if (null == dbTable) {
                System.out.println("No DBtable annotations in class " + className);
                continue;
            }
            String tableName = dbTable.name();
            if (tableName.length() < 1) {
                tableName = cl.getName().toUpperCase();
            }
            List<String> columnDefs = new ArrayList<String>();
            for (Field field : cl.getFields()) {
                String columnName = null;
                Annotation[] anns = field.getAnnotations();
                if (anns.length < 1) {
                    continue;
                }
                if (anns[0] instanceof SQLInteger) {
                    SQLInteger sInt = (SQLInteger) anns[0];
                    if (sInt.name().length() < 1) {
                        columnName = field.getName().toUpperCase();
                    } else {
                        columnName = sInt.name();
                    }
                    columnDefs.add(columnName+ " INT" );
                }

            }

        }
    }
}
