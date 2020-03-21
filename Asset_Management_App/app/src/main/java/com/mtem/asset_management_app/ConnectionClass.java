package com.mtem.asset_management_app;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionClass  {
    //private static String ip = "192.168.0.106:1433";
    private static String ip = "192.236.147.176:1433";
    //private static String ip = "192.236.162.39:1433";
    //private static String ip = "192.168.43.29:1433";
    private static String classs = "net.sourceforge.jtds.jdbc.Driver";
   /*private static String db = "InventoryManagementDataBase";
    private static String user = "IMPServer";
    private static String password = "Welcome123$";
    public static Connection connection;
    public static Statement state;*/

    public ConnectionClass() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
     }
    public Statement getConnection(String database){
        String connectionURL=null;
        Connection connection=null;
        Statement statement=null;
        switch (database) {
            case "InventoryManagementDataBase":
                try {
                    Class.forName(classs);
                    connectionURL = "jdbc:jtds:sqlserver://" + ip + ";"
                            + "databaseName=" + "InventoryManagementDataBase" + ";user=" + "ServerTesting" + ";password="
                            + "Welcome123$" + ";";
                    connection = DriverManager.getConnection(connectionURL);
                    statement = connection.createStatement();
                } catch (SQLException se) {
                    Log.e("ERRO", se.getMessage());
                } catch (ClassNotFoundException e) {
                    Log.e("ERRO", e.getMessage());
                } catch (Exception e) {
                    Log.e("ERRO", e.getMessage());
                }
                break;
            case "GenericDataBase":
                try {
                    Class.forName(classs);
                    connectionURL = "jdbc:jtds:sqlserver://" + ip + ";"
                            + "databaseName=" + "GenericDataBase" + ";user=" + "ServerTesting" + ";password="
                            + "Welcome123$" + ";";
                    connection = DriverManager.getConnection(connectionURL);
                    statement = connection.createStatement();
                } catch (SQLException se) {
                    Log.e("ERRO", se.getMessage());
                } catch (ClassNotFoundException e) {
                    Log.e("ERRO", e.getMessage());
                } catch (Exception e) {
                    Log.e("ERRO", e.getMessage());
                }
                break;
        }
        return statement;
    }
    public ResultSet sqlModule(String operation,String table,String[] column,String condition,String database){
        ResultSet resultSet=null;
         switch (operation){
             case "select":
                 resultSet=selectOperation(operation,table,column,condition,database);
                 break;
        }
        return resultSet;
    }
    public int sqlModule(String operation,String table,String[] column,String condition,String[] values,String database){
        int resultInt=0;
        switch (operation){
            case "insert":
                resultInt=insertOperation(operation,table,column,values,database);
                break;
            case "delete":
                resultInt=deleteOperation(operation,table,condition,database);
                break;
            case "update":
                resultInt=updateOperation(operation,table,column,condition,values,database);
                break;
        }
        return resultInt;
    }
    public ResultSet selectOperation(String operation,String table,String[] columns,String condition,String database){
        ResultSet resultSet=null;
        String query=null;
        try {
        Statement state=getConnection(database);
        query=operation;
        for(int columnCount=0;columnCount<=columns.length-1;columnCount++){
            query+=" "+columns[columnCount];
            if(columnCount!=columns.length-1){
                query+=",";
            }else {
                query+=" ";
            }
        }
        query+=" from "+table+" ";
        if (!(condition==null)){
            query+="where "+condition;
        }
            resultSet=state.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
        e.printStackTrace();
    }
        return resultSet;
    }
    public int insertOperation(String operation,String table,String[] columns,String[] values,String database){
        int resultInt=0;
        String query=null;
        try{
        Statement state=getConnection(database);
        query=operation+" "+"into "+table+" (";
        for(int columnCount=0;columnCount<=columns.length-1;columnCount++){
            query+=" "+columns[columnCount];
            if(columnCount!=columns.length-1){
                query+=",";
            }else {
                query+=") ";
            }
        }
        query+="values('";
        for(int valueCount=0;valueCount<=values.length-1;valueCount++){
            query+=values[valueCount];
            if(valueCount!=values.length-1){
                query+="','";
            }else {
                query+="') ";
            }
        }
            resultInt=state.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
         } catch (Exception e) {
            e.printStackTrace();
        }
        return resultInt;
    }
    public int deleteOperation(String operation,String table,String condition,String database){
        int resultInt=0;
        String query=null;
        Statement state=getConnection(database);


        return resultInt;
    }
    public int updateOperation(String operation,String table,String[] columns,String condition,String[] values,String database){
        int resultInt=0;
        String query=null;
        try{
            Statement state=getConnection(database);
            query=operation+" "+table+" set";
            int valueCount=0;
            for(int columnCount=0;columnCount<=columns.length-1;columnCount++) {
                if (columnCount <= columns.length - 1) {
                    query += " " + columns[columnCount] + "='" + values[valueCount++];
                    if (valueCount <= columns.length - 1) {
                        query += "',";
                    }
                }
            }
            query += "' ";
            if (!(condition==null)){
                query+="where "+condition;
            }
            resultInt=state.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultInt;
    }
}
