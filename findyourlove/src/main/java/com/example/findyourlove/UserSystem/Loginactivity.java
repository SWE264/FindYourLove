package com.example.findyourlove.UserSystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import static com.example.findyourlove.ConnectDatabase.firstUser;

import com.example.findyourlove.ConnectDatabase;
import com.example.findyourlove.MainActivity;
import com.example.findyourlove.Map.MapActivity;
import com.example.findyourlove.R;
import com.example.findyourlove.Util.MD5Util;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class Loginactivity extends Activity {
    public static String accid;
    static Connection conn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            Connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Button loginButton=findViewById(R.id.buttonlogin);
        TextView signUpText=findViewById(R.id.signuptext);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    doLogin();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void doLogin() throws SQLException, ClassNotFoundException {
        EditText loginemail=findViewById(R.id.loginemail);
        EditText loginpsw= findViewById(R.id.loginpsw);
        String strPsw = MD5Util.MD5EncodeUtf8(loginpsw.getText().toString());
        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {
                if (conn==null) Connect();
                String[] infomation=getAccid(null,loginemail.getText().toString().toLowerCase(),strPsw);
                //String[] infomation=getAccid(null,loginemail.getText().toString().toLowerCase(),loginpsw.getText().toString());
                accid=infomation[0];
                LoginInfo info = new LoginInfo(infomation[0],infomation[1]); // infomation[0] = accid ,infomation[1] = token
                RequestCallback<LoginInfo> callback =
                        new RequestCallback<LoginInfo>() {
                            @Override
                            public void onSuccess(LoginInfo param) {
                                NimUIKitImpl.setAccount(param.getAccount());
                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("accid",infomation[0]);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                try {
                                    if(firstUser(Integer.parseInt(accid))){
                                        Intent intent1=new Intent(getApplicationContext(), MapActivity.class);
                                        startActivity(intent1);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                //NimUIKit.startP2PSession(getApplicationContext(),"9283604");
                                //NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
                            }
                            @Override
                            public void onFailed(int code) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Wrong password! ", Toast.LENGTH_SHORT);
                                toast.show();
                            }

                            @Override
                            public void onException(Throwable exception) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Wrong password Or No existing account", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                        };
                NIMClient.getService(AuthService.class).login(info)
                        .setCallback(callback);
                System.out.println("Sha1 is "+ Sha1(getApplicationContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public static String Sha1(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result=hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    //利用用户名和密码来获取ACCID和token
    public static String[] getAccid(String token,String email,String password) throws SQLException, ClassNotFoundException {
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
                while(resultSet.next()){
                    token=resultSet.getString(1);
                    accid=resultSet.getInt(2);

//Here! Store the token in the storage!!!!

            }

        }
        return new String[]{String.valueOf(accid),token};
    }
    //database connection
    public static void Connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("connect to database");
        conn = DriverManager.getConnection("jdbc:mysql://findyourlove.crdb40mgvgxt.us-west-2.rds.amazonaws.com:3306/dating","dating","877152223Zzp!");
        System.out.println("success");
    }
}
