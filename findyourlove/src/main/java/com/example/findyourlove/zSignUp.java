package com.example.findyourlove;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.example.findyourlove.loginactivity.conn;
import static com.example.findyourlove.zConnectDatabase.isExist;

public class zSignUp extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zactivity_signup);


    }
    public void signUp(View view){
        EditText email=findViewById(R.id.signinemail);
        EditText psd=findViewById(R.id.signinpsw);
        String stringEmail=email.getText().toString();
        String stringPsd=psd.getText().toString();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {
                if(!isExist(stringEmail)) {
                    createUser(stringEmail, stringPsd);
                    Toast toast = Toast.makeText(getApplicationContext(), "Create success ", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Account has existed ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }


    public  String genToken(int accid,String name,String email) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/user/create.action";
        HttpPost httpPost = new HttpPost(url);
        String appKey = "26462577589cd3eedea15981b5ce9b2c";
        String appSecret = "769db999fdba";
        String nonce = NonceBuilder.genNonce(20); //GEN!!!
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");


        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", Integer.toString(accid)));   //要写！！
        nvps.add(new BasicNameValuePair("name", name));
        nvps.add(new BasicNameValuePair("email", email)) ; //要写！！
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);


        // 打印执行结果
        String responseString=EntityUtils.toString(response.getEntity());
     //   System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
System.out.println(responseString);
        JSONObject jo=new JSONObject(responseString);
        int result=jo.getInt("code");
        String token=null;
        if(result==200){//NORMAl
            token=jo.getJSONObject("info").getString("token");
        }
        else {
            throw new Exception("Error in generating token, error code: "+result);
        }

        return token;

    }
    public void createUser( String email, String password) throws Exception {
        int accid=(int)(System.currentTimeMillis()%Math.pow(10,8));

        String name=email;
String token=genToken(accid,name,email);
if(token!=null){
    System.out.println(token);
    zConnectDatabase.addUser(accid,token,email,password);
    zConnectDatabase.initialLocation(accid);
}
else{
    System.out.println("token fail");
}


    }
}

class CheckSumBuilder {

    public static String getCheckSum(String appSecret, String nonce, String curTime) {
        return encode("sha1", appSecret + nonce + curTime);
    }

    private static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }


    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

}

class NonceBuilder{
    public static String genNonce(int length){
       long seed = System.currentTimeMillis()%100;
        Random random=new Random(seed);
        StringBuffer stringBuffer=new StringBuffer("");
        for(int a=0;a<length;a++){
           int i= random.nextInt(36);
            if(i>=0&&i<10){
                stringBuffer.append(i);
            }
            else{
                i-=10;
                stringBuffer.append((char)('a'+i));
            }
        }
        return stringBuffer.toString();


    }




}
