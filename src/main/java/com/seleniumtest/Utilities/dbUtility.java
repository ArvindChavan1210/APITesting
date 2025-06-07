package com.seleniumtest.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

public class dbUtility {

    static String url = "jdbc:mysql://localhost:3306/classicmodels";
    static String userName = "root";
    static String password = "amc_6683";

    public static Connection makeConnection() {
        try {
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    public static ArrayList<HashMap<String, String>> getTableData(String tableName) {
        try {
            ResultSet rs = makeConnection().createStatement().executeQuery("select * from " + tableName + ";");
            ResultSetMetaData rsm = rs.getMetaData();
            ArrayList<HashMap<String, String>> tableData = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> rowData = new HashMap<>();
                for (int i = 1; i < rsm.getColumnCount(); i++) {
                    rowData.put(rsm.getColumnName(i), rs.getString(i));
                }
                tableData.add(rowData);
            }
            return tableData;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
       String firstName= getTableData("employees").get(0).get("firstName");
       String lastName= getTableData("employees").get(0).get("lastName");
       String fullname=firstName+" "+lastName;
       System.out.println(fullname);
    }

}
