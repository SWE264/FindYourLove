package com.example.findyourlove;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class user_db {
    static Connection conn;
    public static void Connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("connect to database");
        conn = DriverManager.getConnection("jdbc:mysql://findyourlove.crdb40mgvgxt.us-west-2.rds.amazonaws.com:3306/dating","dating","877152223Zzp!");
    }

    public static String getName2(int id) throws SQLException, ClassNotFoundException {
        if(conn==null)
            user_db.Connect();
        String s = null;
        PreparedStatement preparedStatement=conn.prepareStatement("SELECT name FROM dating.user WHERE accid = ?;");
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
               s = resultSet.getString(1);
        }
        return s;
    }

    public static String getBirth2(int id) throws SQLException, ClassNotFoundException {
        if(conn==null)
            user_db.Connect();
        String s = null;
        PreparedStatement preparedStatement=conn.prepareStatement("SELECT birthday FROM dating.user WHERE accid = ?;");
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            s = resultSet.getString(1);
        }
        return s;
    }

    public static String getRegion2(int id) throws SQLException, ClassNotFoundException {
        if(conn==null)
            user_db.Connect();
        String s = null;
        PreparedStatement preparedStatement=conn.prepareStatement("SELECT region FROM dating.user WHERE accid = ?;");
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            s = resultSet.getString(1);
        }
        return s;
    }

    public static String getGender2(int id) throws SQLException, ClassNotFoundException {
        if(conn==null)
            user_db.Connect();
        String s = null;
        PreparedStatement preparedStatement=conn.prepareStatement("SELECT gender FROM dating.user WHERE accid = ?;");
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            s = resultSet.getString(1);
        }
        return s;
    }

    public static void Update2(int id, String name, String region, String birthday, String gender) throws SQLException, ClassNotFoundException {
        if(conn==null)
            user_db.Connect();
        String s = null;
        //PreparedStatement preparedStatement=conn.prepareStatement("UPDATE dating.Test SET name = ? WHERE id = 1;");
        //System.out.println("UPDATE准备执行");
        PreparedStatement preparedStatement=conn.prepareStatement("UPDATE user SET name = ?, region = ?, birthday = ?, gender = ? WHERE accid = ?;");
        //PreparedStatement preparedStatement=conn.prepareStatement("INSERT INTO Test(id, name, birthday, region, gender) VALUES(?,?,?,?,?)");
        preparedStatement.setInt(5,id);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,region);
        preparedStatement.setString(3,birthday);
        preparedStatement.setString(4,gender);
        //preparedStatement.setString(5,"m");
        preparedStatement.execute();
       //System.out.println("UPDATE执行成功");
    }

}
