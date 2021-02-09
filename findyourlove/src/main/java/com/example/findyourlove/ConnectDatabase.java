package com.example.findyourlove;

import com.microsoft.maps.Geopoint;

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
        conn = DriverManager.getConnection("jdbc:mysql://findyourlove.crdb40mgvgxt.us-west-2.rds.amazonaws.com:3306/dating","dating","877152223Zzp!");
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
    ////////已经在LoginActivity中做了相同方法，此方法抛弃

//    public static int getAccid(String token,String email,String password) throws SQLException, ClassNotFoundException {
//        //two log in method. The first one is log in with token, returning accid. Email and password are null. The second one is log in with email and password, returning token and accid. Token should be null
//        int accid=0;
//        if(conn==null)
//            ConnectDatabase.Connect();
//        if(token!=null) {
//            PreparedStatement preparedStatement = conn.prepareStatement("SELECT accid FROM user WHERE token=?");
//            preparedStatement.setString(1, token);
//            ResultSet resultSet=preparedStatement.executeQuery();
//
//            while(resultSet.next()){
//                accid=resultSet.getInt(1);
//            }
//        }
//        else{
//            PreparedStatement preparedStatement=conn.prepareStatement("SELECT token, accid FROM user WHERE email=? AND password=?");
//            preparedStatement.setString(1,email);
//            preparedStatement.setString(2,password);
//            ResultSet resultSet=preparedStatement.executeQuery();
//            String token2=null;
//
//
//            if(resultSet.getFetchSize()==0){
//                System.out.println("ERROR: EMAIL OR PASSWORD");
//            }
//            else{
//                while(resultSet.next()){
//                    token=resultSet.getString(1);
//                    accid=resultSet.getInt(2);
//
////Here! Store the token in the storage!!!!
//                }
//            }
//
//        }
//        return accid;
//    }
    public static boolean isExist(String email) throws SQLException, ClassNotFoundException {  //if the return value is true, it means this email exists in the database, the register should be refused
        if(conn==null)
            ConnectDatabase.Connect();
        PreparedStatement preparedStatement=conn.prepareStatement("SELECT email FROM user WHERE email = ?");
        preparedStatement.setString(1,email);
        ResultSet resultSet=preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static ResultSet getSurroundingUserLocation(Geopoint geopoint,double radius,int accid) throws SQLException, ClassNotFoundException {
        if(conn==null)
            ConnectDatabase.Connect();
        double longitude=geopoint.getPosition().getLongitude();
        double latitude=geopoint.getPosition().getLatitude();
        PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM Location WHERE longitude - ? < ? and latitude - ? < ? and accid != ?");
        preparedStatement.setDouble(1,longitude);
        preparedStatement.setDouble(2,radius);
        preparedStatement.setDouble(3,latitude);
        preparedStatement.setDouble(4,radius);
        preparedStatement.setInt(5,accid);

        ResultSet resultSet=preparedStatement.executeQuery();


        return  resultSet;
    }
    //zero is name, 1 is gender
    public static String[] getUser(int accid) throws SQLException, ClassNotFoundException {
        if(conn==null)
            ConnectDatabase.Connect();
        PreparedStatement preparedStatement=conn.prepareStatement("SELECT name, gender FROM user WHERE accid = ?");
        preparedStatement.setInt(1,accid);
        String[] user=new String[2];
        ResultSet resultSet=preparedStatement.executeQuery();
        resultSet.next();
        user[0]=resultSet.getString(1);
        user[1]=resultSet.getString(2);

        return user;
    }
    public static void uploadLocation(Geopoint geopoint,int accid) throws SQLException, ClassNotFoundException {
        if(conn==null)
            ConnectDatabase.Connect();
        PreparedStatement preparedStatement=conn.prepareStatement("UPDATE Location SET latitude = ?, longitude = ? WHERE accid = ?");
        preparedStatement.setDouble(1,geopoint.getPosition().getLatitude());
        preparedStatement.setDouble(2,geopoint.getPosition().getLongitude());
        preparedStatement.setInt(3,accid);
        preparedStatement.execute();


    }
    public static void initialLocation(int accid) throws SQLException, ClassNotFoundException {
        if(conn==null)
            ConnectDatabase.Connect();
        PreparedStatement preparedStatement=conn.prepareStatement("INSERT INTO Location VALUES(?,?,?,?)");
        preparedStatement.setInt(1,accid);
        preparedStatement.setDouble(2,-100);
        preparedStatement.setDouble(3,-100);
        preparedStatement.setBoolean(4,true);
        preparedStatement.execute();


    }
    public static boolean firstUser(int accid) throws SQLException, ClassNotFoundException {
        if(conn==null)
            ConnectDatabase.Connect();

        PreparedStatement preparedStatement=conn.prepareStatement("SELECT latitude, longitude FROM Location WHERE accid = ?");
        preparedStatement.setInt(1,accid);
        ResultSet resultSet=preparedStatement.executeQuery();
        resultSet.next();
        double latitude=resultSet.getDouble(1);
        double longitude=resultSet.getDouble(2);
        if(latitude==-100&&longitude==-100){
            return true;
        }
        else {
            return false;
        }
    }
    public static String getName2(int id) throws SQLException, ClassNotFoundException {
        if(conn==null)
            ConnectDatabase.Connect();
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
            ConnectDatabase.Connect();
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
            ConnectDatabase.Connect();
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
            ConnectDatabase.Connect();
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
            ConnectDatabase.Connect();
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
