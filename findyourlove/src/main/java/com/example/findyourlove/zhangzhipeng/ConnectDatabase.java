package com.example.findyourlove.zhangzhipeng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDatabase {
    static Connection conn;
    public static void Connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("connect to database");
        conn = DriverManager.getConnection("jdbc:mysql://date.ihghotel.cn:3306/dating","dating","877152223Zzp!");
    }
    public static void addUser(int accid, String token, String email, String password) throws SQLException, ClassNotFoundException {
        if(conn==null)
            ConnectDatabase.Connect();
        PreparedStatement preparedStatement=conn.prepareStatement("INSERT INTO user(accid, token, email, password) VALUES(?,?,?,?)");
        preparedStatement.setInt(1,accid);
        preparedStatement.setString(2,token);
        preparedStatement.setString(3,email);
        preparedStatement.setString(4,password);
        preparedStatement.execute();
    }
    public static int getAccid(String token,String email,String password) throws SQLException, ClassNotFoundException {
       //two log in method. The first one is log in with token, returning accid. Email and password are null. The second one is log in with email and password, returning token and accid. Token should be null
        int accid=0;
        if(conn==null)
            ConnectDatabase.Connect();
        if(token!=null) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT accid FROM user WHERE token=?");
            preparedStatement.setString(1, token);
            ResultSet resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                accid=resultSet.getInt(1);
            }
        }
        else{
            PreparedStatement preparedStatement=conn.prepareStatement("SELECT token, accid FROM user WHERE email=? AND password=?");
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet=preparedStatement.executeQuery();
            String token2=null;


            if(resultSet.getFetchSize()==0){
                System.out.println("ERROR: EMAIL OR PASSWORD");
            }
            else{
                while(resultSet.next()){
token=resultSet.getString(1);
accid=resultSet.getInt(2);

//Here! Store the token in the storage!!!!
                }
            }

        }
        return accid;
    }
}
