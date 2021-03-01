package com.example.findyourlove.PersonalInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

import com.example.findyourlove.ConnectDatabase;
import com.example.findyourlove.MainActivity;
import com.example.findyourlove.R;
import com.example.findyourlove.UserSystem.Loginactivity;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SelectPortraitActivity extends AppCompatActivity {
    MainActivity mainActivity=new MainActivity();
    private int id = Integer.parseInt(Loginactivity.accid);
    public static Connection conn;

    public static void Connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("connect to database");
        conn = DriverManager.getConnection("jdbc:mysql://findyourlove.crdb40mgvgxt.us-west-2.rds.amazonaws.com:3306/dating","dating","877152223Zzp!");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_portrait);
        HeadImageView portrait_1=findViewById(R.id.portrait_1);
        HeadImageView portrait_2=findViewById(R.id.portrait_2);
        HeadImageView portrait_3=findViewById(R.id.portrait_3);
        HeadImageView portrait_4=findViewById(R.id.portrait_4);
        HeadImageView portrait_5=findViewById(R.id.portrait_5);
        HeadImageView portrait_6=findViewById(R.id.portrait_6);
        HeadImageView portrait_7=findViewById(R.id.portrait_7);
        HeadImageView portrait_8=findViewById(R.id.portrait_8);
        HeadImageView portrait_9=findViewById(R.id.portrait_9);

        portrait_1.loadBuddyAvatar(1);
        portrait_2.loadBuddyAvatar(2);
        portrait_3.loadBuddyAvatar(3);
        portrait_4.loadBuddyAvatar(4);
        portrait_5.loadBuddyAvatar(5);
        portrait_6.loadBuddyAvatar(6);
        portrait_7.loadBuddyAvatar(7);
        portrait_8.loadBuddyAvatar(8);
        portrait_9.loadBuddyAvatar(9);
        portrait_1.setOnClickListener(this::onClick);
        portrait_2.setOnClickListener(this::onClick);
        portrait_3.setOnClickListener(this::onClick);
        portrait_4.setOnClickListener(this::onClick);
        portrait_5.setOnClickListener(this::onClick);
        portrait_6.setOnClickListener(this::onClick);
        portrait_7.setOnClickListener(this::onClick);
        portrait_8.setOnClickListener(this::onClick);
        portrait_9.setOnClickListener(this::onClick);


    }

    public void onClick(View v) {
        try {
            System.out.println("Click the first Avatar");
        //Intent intent = new Intent(this, MainActivity.class);
        switch (v.getId()){
            //navigation跳转
            case R.id.portrait_1:

                    changeAvatar(1);
                    finish();
                break;
            case R.id.portrait_2:
                changeAvatar(2);
                finish();
                break;
            case R.id.portrait_3:
                changeAvatar(3);
                finish();
                break;
            case R.id.portrait_4:
                changeAvatar(4);
                finish();
                break;
            case R.id.portrait_5:
                changeAvatar(5);
                finish();
                break;
            case R.id.portrait_6:
                changeAvatar(6);
                finish();
                break;
            case R.id.portrait_7:
                changeAvatar(7);
                finish();
                break;
            case R.id.portrait_8:
                changeAvatar(8);
                finish();
                break;
            case R.id.portrait_9:
                changeAvatar(9);
                finish();
                break;
            default:
                break;
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void changeAvatar(int num) throws SQLException {
        if(conn==null){
            try {
                Connect();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        PreparedStatement preparedStatement=conn.prepareStatement("update user set avatar=? where accid=?");
        preparedStatement.setInt(1,num);
        preparedStatement.setInt(2,id);
        preparedStatement.execute();
    }


}