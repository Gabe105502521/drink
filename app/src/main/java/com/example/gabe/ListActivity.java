package com.example.gabe;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class ListActivity extends AppCompatActivity implements LocationListener {
    public static final String Tag = "ListActivity88";
    public static double lat, lng;
    private LocationManager locationManager;
    public static ListView listView;
    public static Location location;
    public static int cnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) { //android 版本 > android版 api版本
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        /*if(location == null){
        }else {
            lng = location.getLongitude();
            lat = location.getLatitude();
        }*/
        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+", "+%+lng+"&radius=500&keyword=%E9%A3%B2%E6%96%99&key=AIzaSyBMum64_lpZuX7_M0ua4Mwc8aqz3CyArLI
        //透過gps, 更新, 間隔幾公尺
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        if(location == null){
            Toast.makeText(this," !?",Toast.LENGTH_SHORT).show();
        }
        cnt += 1; //第二次crash?
        getList();
    }


    private void getList(){
        fetchData process = new fetchData(this);
        process.execute();
    }
    @Override
    public void onLocationChanged(Location location) {
        //Log.i(Tag, "onLocationChanged"+location.toString());
        //tv.setText(""+location.getLongitude()+","+location.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        Log.i(Tag, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(Tag, "onProviderEnbled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(Tag, "onProviderDisabled");  //gps關掉
    }


}
