package com.example.match;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {
    private int[] arrayPicture = new int[] {R.drawable.p1,R.drawable.p2, R.drawable.p3};
    private ImageSwitcher IS;
    private int index;
    private float touchDownX;
    private float touchUpX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IS = (ImageSwitcher) findViewById(R.id.ImageSwithcher);
        IS.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out));
        IS.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_in));
        IS.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {

                ImageView IV = new ImageView(MainActivity.this);
                IV.setImageResource(arrayPicture[index]);
                return IV;
            }
        });

        IS.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    touchDownX = event.getX();
                    return true;

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    touchUpX = event.getX();
                    if(touchUpX-touchDownX>100){
                        index = index==0?arrayPicture.length-1:index-1;
                        IS.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_in));
                        IS.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out));
                        IS.setImageResource(arrayPicture[index]);
                    }else if(touchDownX-touchUpX>100){
                        index = index==arrayPicture.length?0:index+1;
                        IS.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_in));
                        IS.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out));
                        IS.setImageResource(arrayPicture[index]);
                    }
                    return true;
                }
                return false;
            }
        });

    }
}
