package com.example.findyourlove.PersonalInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.SQLException;

import com.example.findyourlove.ConnectDatabase;
import com.example.findyourlove.UserSystem.Loginactivity;
import com.example.findyourlove.MainActivity;
import com.example.findyourlove.R;
import com.netease.nim.uikit.api.NimUIKit;


import android.widget.Button;


public class PersonInfoActivity extends AppCompatActivity {
    private ItemGroup ig_id,ig_name,ig_gender,ig_region,ig_brithday;

    private int id = Integer.parseInt(Loginactivity.accid);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=this.getIntent().getExtras();
        System.out.println("ACCID IS " + bundle.getInt("accid"));
        id=bundle.getInt("accid");
        setContentView(R.layout.activity_person_info);

        ig_id = (ItemGroup)findViewById(R.id.ig_id);
        ig_name = (ItemGroup)findViewById(R.id.ig_name);
        ig_gender = (ItemGroup)findViewById(R.id.ig_gender);
        ig_region = (ItemGroup)findViewById(R.id.ig_region);
        ig_brithday = (ItemGroup)findViewById(R.id.ig_brithday);

        Button chatbutton=findViewById(R.id.Chat);
        chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NimUIKit.startP2PSession(getApplicationContext(),Integer.toString(id));
            }
        });
        Button back=findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //使箭头不可见
        ig_name.invisible();
        ig_gender.invisible();
        ig_region.invisible();
        ig_brithday.invisible();

        ig_id.getContentEdit().setText(String.valueOf(id) + "               ");

        //从数据读取数据
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            Initial_Thread.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    Thread Initial_Thread =new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("Ready to upload activity");
                //ig_name.setTConnectDatabase.getName2(id);
                ig_name.getContentEdit().setText(ConnectDatabase.getName2(id));
                ig_brithday.getContentEdit().setText(ConnectDatabase.getBirth2(id));
                ig_gender.getContentEdit().setText(ConnectDatabase.getGender2(id));
                ig_region.getContentEdit().setText(ConnectDatabase.getRegion2(id));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    });




}
