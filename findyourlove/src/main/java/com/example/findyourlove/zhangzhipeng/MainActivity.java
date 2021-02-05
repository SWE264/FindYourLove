package com.example.findyourlove.zhangzhipeng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.findyourlove.R;
import com.microsoft.maps.Geopoint;
import com.microsoft.maps.MapAnimationKind;
import com.microsoft.maps.MapElementLayer;
import com.microsoft.maps.MapIcon;
import com.microsoft.maps.MapRenderMode;
import com.microsoft.maps.MapScene;
import com.microsoft.maps.MapView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;


public class MainActivity extends Activity implements AMapLocationListener {
    private MapView mMapView;
    private MapElementLayer mPinLayer;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    private boolean begin=true;
    RecyclerView showUser;
private AMapLocation currentLocation;
static  List<TemporaryData> Demodata=new ArrayList<TemporaryData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Demodata.add(new TemporaryData(1,"demoUser","Prefer Not to Say",32.0652,118.62));
        Demodata.add(new TemporaryData(200,"demoUser2","Male",32.05,118.63));


String[] permission={"android.permission.ACCESS_COARSE_LOCATION","android.permission.INTERNET"};
this.requestPermissions(permission,1);
        mlocationClient = new AMapLocationClient(this);
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mlocationClient.startLocation();
        mMapView = new MapView(this, MapRenderMode.VECTOR);  // or use MapRenderMode.RASTER for 2D map
        mMapView.setCredentialsKey("AmQmX465OwoobbQRL2tex9u96OIAbPWeEEcrPKkK9LShO3j7Ah3ya8nifAaxhtFJ");
        ((FrameLayout)findViewById(R.id.map_view)).addView(mMapView);
        mPinLayer = new MapElementLayer();
        mMapView.getLayers().add(mPinLayer);




        showUser  =findViewById(R.id.recyclerView);
        showUser.setLayoutManager(new LinearLayoutManager(this));
        showUser.setAdapter(new HomeAdapter(this));

        addPoint(new Geopoint(32.054,118.54));
        addPoint(new Geopoint(32.063,118.62));

    }
    @Override
    protected void onStart() {

        super.onStart();

        mMapView.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    public void addPoint(Geopoint location){
          // your pin lat-long coordinates
      //  String title = "Meow";       // title to be shown next to the pin
     //   Bitmap pinBitmap = ...   // your pin graphic (optional)

        MapIcon pushpin = new MapIcon();
        pushpin.setLocation(location);
      //  pushpin.setTitle(title);
     //   pushpin.setImage(new MapImage(pinBitmap));

        mPinLayer.getElements().add(pushpin);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {


        System.out.println("hhhhhhhhhhhhhhhhh");
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                currentLocation=amapLocation;
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                System.out.println( amapLocation.getLocationType()+"la"+ amapLocation.getLatitude()+"lo"+amapLocation.getLongitude());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                //addPoint(new Geopoint(amapLocation.getLatitude(),amapLocation.getLongitude()));
                if(begin) {
                    begin=false;
                    Geopoint currentPoint = new Geopoint(currentLocation.getLatitude(), currentLocation.getLongitude());
                    mMapView.setScene(
                            MapScene.createFromLocationAndZoomLevel(currentPoint, 10),
                            MapAnimationKind.NONE);
                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}

class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ItemViewHolder>{
    public Context mContext;

    public HomeAdapter(Context mainActivity) {
        mContext=mainActivity;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewHolder holder;
        holder = new ItemViewHolder(LayoutInflater.from(
                   mContext).inflate(R.layout.zitem, parent,    //Change here！！！！！！！！
                false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
holder.gender.setText("Gender: "+MainActivity.Demodata.get(position).getGender());
holder.distance.setText("Distance: "+Double.toString(MainActivity.Demodata.get(position).getDistance())+" km");
holder.button.setOnClickListener(a->{
    Intent intent = new Intent(mContext,ChatWindows.class);
    Bundle bundle=new Bundle();
    bundle.putString("userName",MainActivity.Demodata.get(position).userName);
    intent.putExtras(bundle);
    startActivity(mContext,intent,bundle);
});
    }

    @Override
    public int getItemCount() {
        return MainActivity.Demodata.size();
    }
    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView distance;
        TextView gender;
        Button button;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            distance=itemView.findViewById(R.id.Distance);
            gender=itemView.findViewById(R.id.Gender);
            button=itemView.findViewById(R.id.chatButton);
        }
    }
}

