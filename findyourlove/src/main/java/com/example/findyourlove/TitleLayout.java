package com.example.findyourlove;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



public class TitleLayout extends LinearLayout {
    //private ImageView iv_backward;
    private TextView tv_title, tv_forward;

    private FragmentManager manager;
    private FragmentTransaction ft;

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LinearLayout bar_title = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.bar_title, this);
        //iv_backward = (ImageView) bar_title.findViewById(R.id.iv_backward);
        tv_title = (TextView) bar_title.findViewById(R.id.tv_title);
        tv_forward = (TextView) bar_title.findViewById(R.id.tv_forward);

            //tv_forward.setText("Edit");
           //tv_title.setText("Person Info");


        //设置监听器
        //如果点击back则结束活动
        tv_forward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
/*    public TextView getTextView_forward(){
        return tv_forward;
    }*/
}
