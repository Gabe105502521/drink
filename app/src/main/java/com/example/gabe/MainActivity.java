package com.example.gabe;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;

public class    MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    public static double lat, lng;
    public static Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            openGPS();
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) { //android 版本 > android版 api版本
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        location = getLastKnownLocation();

        Button btn_random = findViewById(R.id.btn_random);
        Button btn_search = findViewById(R.id.btn_search);
        Button btn_list = findViewById(R.id.btn_list);
        Button btn_maker = findViewById(R.id.btn_maker);

        //ImageButton imageButton11 = findViewById(R.id.imageButton11);

        btn_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, random_Activity.class);
                startActivity(intent);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, search.class);
                startActivity(intent);
            }
        });
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
        btn_maker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, makerActivity.class);
                startActivity(intent);
            }
        });

        getList();
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, (LocationListener) this);
        //Toast.makeText(MainActivity.this, ""+location.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    private void getList(){
        fetchData process = new fetchData(MainActivity.this);
        process.execute();
    }

    private Location getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getAllProviders();
        Location bestLocation = null;
        for (String provider : providers) {

            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    private void openGPS() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("提示")
                .setMessage("沒有開啟定位")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("打開gps", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
