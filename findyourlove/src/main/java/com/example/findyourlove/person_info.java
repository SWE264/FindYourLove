package com.example.findyourlove;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.SQLException;

import com.netease.nim.uikit.api.NimUIKit;


import android.widget.Button;


public class person_info extends AppCompatActivity {
    private ItemGroup ig_id,ig_name,ig_gender,ig_region,ig_brithday;

    private int id = Integer.parseInt(loginactivity.accid);



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
                Intent intent=new Intent(getApplicationContext(),Main.class);
                startActivity(intent);
            }
        });

        //使箭头不可见
        ig_name.invisible();
        ig_gender.invisible();
        ig_region.invisible();
        ig_brithday.invisible();

        ig_id.getContentEdt().setText(String.valueOf(id) + "               ");

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
                System.out.println("Ready to upload");
                //ig_name.setTuser_db.getName2(id);
                ig_name.getContentEdt().setText(user_db.getName2(id));
                ig_brithday.getContentEdt().setText(user_db.getBirth2(id));
                ig_gender.getContentEdt().setText(user_db.getGender2(id));
                ig_region.getContentEdt().setText(user_db.getRegion2(id));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    });


/*    public void chat(View view){
        Bundle bundle=new Bundle();
        bundle.putInt("accid",id);
        Intent intent=new Intent(this,message.class);
        startActivity(intent,bundle);

        NimUIKit.startP2PSession(getApplicationContext(),Integer.toString(id));
    }*/

}
