package com.example.gabe;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class fetchData extends AsyncTask<Void, Void, Void> {
    private String data;
    public static List<drinkshop> shops = new ArrayList<drinkshop>();
    private Context mContext;
    private Location mlocation;
    public fetchData (Context context, Location location) {
        mContext = context;
        mlocation = location;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url =  new URL("https://api.myjson.com/bins/189646");
//            URL url =  new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+ListActivity.lat+", "+ListActivity.lng+"&radius=500&keyword="+URLEncoder.encode("飲料")+ "&key=AIzaSyBMum64_lpZuX7_M0ua4Mwc8aqz3CyArLI");
//            https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=24.968052,%20%20121.192143&radius=500&keyword=%E9%A3%B2%E6%96%99&key=AIzaSyBMum64_lpZuX7_M0ua4Mwc8aqz3CyArLI
            HttpURLConnection htTpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = htTpURLConnection.getInputStream();
            BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line!=null){
                line = bufferedReader.readLine();
                data = data + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //location == null 會造成程式crush, 現在的寫法可能會須等一陣子才拿到

        String crappyPrefix = "null";
        if (data.startsWith(crappyPrefix)) {
            data = data.substring(crappyPrefix.length(), data.length());
        } //報錯加的Value null of type org.json.JSONObject$1 cannot be converted to JSONObject

        try {
            //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONObject jsonObject = new JSONObject(data);
            JSONArray array = jsonObject.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject2 = array.getJSONObject(i);

                //get lat, lng
                String geometry = jsonObject2.getString("geometry");
                JSONObject geo_jsonObject = new JSONObject(geometry);
                String location = geo_jsonObject.getString("location");
                JSONObject loca_jsonObject = new JSONObject(location);
                String lat = loca_jsonObject.getString("lat");
                String lng = loca_jsonObject.getString("lng");

                //get name、vicinity、rating
                String name = jsonObject2.getString("name");
                String vicinity = jsonObject2.getString("vicinity");
                String rating = jsonObject2.getString("rating");
                drinkshop shop=new drinkshop(name, Double.parseDouble(lat), Double.parseDouble(lng), vicinity, Double.parseDouble(rating));
                shops.add(shop);
            }
        } catch (JSONException e) {
            Log.e(ListActivity.Tag, Log.getStackTraceString(e));
        }
        //sort
        sortRating(shops);

        List<HashMap<String, String>> list = new ArrayList<>();
        String shopsName[] = new String[20];
        String shopsRating[] = new String[20];
        String shopsDistance[] = new String[20];
        for (int i=0; i<shops.size(); i++) {
            shopsName[i] = shops.get(i).name;
        }
        for (int i = 0; i < shops.size(); i++) {
            shopsRating[i] = shops.get(i).rating.toString();
        }
        Double mlng = mlocation.getLongitude();
        Double mlat = mlocation.getLatitude();
        for (int i = 0; i < shops.size(); i++) {
            shopsDistance[i] = Double.toString(gps2m(mlat, mlng, shops.get(i).lat, shops.get(i).lng));
        }

        for(int i = 0 ; i < 20 ; i++){
            HashMap<String , String> hashMap = new HashMap<>();
            hashMap.put("shopsName" , shopsName[i]);
            hashMap.put("shopsRating" , "評分: "+shopsRating[i]+"    距離: "+shopsDistance[i] + "公尺");
            //將shopsName , shopsRating存入HashMap之中
            list.add(hashMap);
            //把HashMap存入list之中
        }
        ListAdapter listAdapter = new SimpleAdapter(
                mContext,
                list,
                android.R.layout.simple_list_item_2 ,
                new String[]{"shopsName" , "shopsRating"} ,
                new int[]{android.R.id.text1 , android.R.id.text2});
        // 5個參數 : context , List , layout , key1 & key2 , text1 & text2
        ListActivity.listView.setAdapter(listAdapter);
        ListActivity.listView.setOnItemClickListener(onClickListView);
}
    //sort by rating
    public static void sortRating(List<drinkshop> list){
        Collections.sort(list);//编译通过；
    }

    //經緯算距離
    private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double EARTH_RADIUS = 6378137.0;
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)+
                Math.cos(radLat1) * Math.cos(radLat2)
                        * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }



    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast 快顯功能 第三個參數 Toast.LENGTH_SHORT 2秒  LENGTH_LONG 5秒
            Toast.makeText(mContext,"點選第 "+(position +1) +" 個 \n內容：",Toast.LENGTH_SHORT).show();
        }
    };
}
