package com.example.findyourlove.PersonalInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

import com.example.findyourlove.R;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;

public class SelectPortraitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_portrait);
        RoundImageView portrait_1=findViewById(R.id.portrait_1);
        RoundImageView portrait_2=findViewById(R.id.portrait_2);
        RoundImageView portrait_3=findViewById(R.id.portrait_3);
        RoundImageView portrait_4=findViewById(R.id.portrait_4);
        RoundImageView portrait_5=findViewById(R.id.portrait_5);
        RoundImageView portrait_6=findViewById(R.id.portrait_6);
        RoundImageView portrait_7=findViewById(R.id.portrait_7);
        RoundImageView portrait_8=findViewById(R.id.portrait_8);
        RoundImageView portrait_9=findViewById(R.id.portrait_9);

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