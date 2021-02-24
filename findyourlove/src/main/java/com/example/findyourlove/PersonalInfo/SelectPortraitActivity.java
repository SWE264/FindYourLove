package com.example.findyourlove.PersonalInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

import com.example.findyourlove.MainActivity;
import com.example.findyourlove.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;

public class SelectPortraitActivity extends AppCompatActivity {
    MainActivity mainActivity=new MainActivity();
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

    public void onClick(View v){

        Intent intent = new Intent(this, PersonInfoActivity.class);
        switch (v.getId()){
            //navigation跳转
            case R.id.portrait_1:
                finish();
                break;
            case R.id.portrait_2:
                startActivity(intent);
                break;
            case R.id.portrait_3:
                startActivity(intent);
                break;
            case R.id.portrait_4:
                startActivity(intent);
                break;
            case R.id.portrait_5:
                startActivity(intent);
                break;
            case R.id.portrait_6:
                startActivity(intent);
                break;
            case R.id.portrait_7:
                startActivity(intent);
                break;
            case R.id.portrait_8:
                startActivity(intent);
                break;
            case R.id.portrait_9:
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}