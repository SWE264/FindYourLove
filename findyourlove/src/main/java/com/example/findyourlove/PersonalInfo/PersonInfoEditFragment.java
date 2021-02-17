package com.example.findyourlove.PersonalInfo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.findyourlove.ConnectDatabase;
import com.example.findyourlove.UserSystem.Loginactivity;
import com.example.findyourlove.R;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PersonInfoEditFragment extends Fragment {
    private ItemGroup ig_id,ig_name,ig_gender,ig_region,ig_brithday;
    private int id = Integer.parseInt(Loginactivity.accid);
    private TextView tv_forward;

    //选择器
    private OptionsPickerView pvOptions;
    //性别选择器
    private ArrayList<String> optionsItems_gender = new ArrayList<>();
    //日期选择器
    private TimePickerView pvTime;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.fragment_person_info_basic, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ig_id = view.findViewById(R.id.ig_id);
        ig_name = view.findViewById(R.id.ig_name);
        ig_gender = view.findViewById(R.id.ig_gender);
        ig_region = view.findViewById(R.id.ig_region);
        ig_brithday = view.findViewById(R.id.ig_brithday);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            Initial_Thread.run();
        }catch (Exception e){
            e.printStackTrace();
        }
        //使EditTextView可以编辑
        ig_name.editable();
        ig_region.editable();

        //为性别选择器添加数据
        optionsItems_gender.add(new String("Unknown"));
        optionsItems_gender.add(new String("Male"));
        optionsItems_gender.add(new String("Female"));


        //将右上角文本设为SAVE
        tv_forward = view.findViewById(R.id.edit_save);
        tv_forward.setText("Save");

        //设置监听
        ig_brithday.setOnClickListener(this::onClick);
        ig_gender.setOnClickListener(this::onClick);
        tv_forward.setOnClickListener(this::onClick);

        //根据全局变量设置id
        ig_id.getContentEdit().setText(String.valueOf(id) + "               ");

        //从数据读取数据

    }

    Thread Initial_Thread =new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("Ready to upload ");
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

    public void onClick(View v){
        switch (v.getId()){
            //Navigation 跳转
            case R.id.edit_save:
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try{
                    Update_Thread.run();
                }catch (Exception e){
                    e.printStackTrace();
                }
                Navigation.findNavController(v).navigate(R.id.action_navigation_person_info_edit_to_navigation_person_info);
                break;

            //点击后调出日期选择器
            case R.id.ig_brithday:
                TimePickerView pvTime = new TimePickerBuilder(this.getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date,View v) {//选中事件回调
                       // tvTime.setText(getTime(date));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        ig_brithday.getContentEdit().setText(sdf.format(date));
                    }
                }).build();
                pvTime.show();
                break;

            //性别选择器
            case R.id.ig_gender:
                pvOptions = new OptionsPickerBuilder(this.getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        String tx = optionsItems_gender.get(options1);
                        ig_gender.getContentEdit().setText(tx);
                    }
                }).setCancelColor(Color.GRAY).build();
                pvOptions.setPicker(optionsItems_gender);
                pvOptions.show();
                break;

            default:
                break;

        }
    }

    //数据库UPDATE方法
    Thread Update_Thread = new Thread(new Runnable(){
        public void run() {
            try {
                ConnectDatabase.Update2(id,ig_name.getText(),ig_region.getText(),ig_brithday.getText(),ig_gender.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    });

}
