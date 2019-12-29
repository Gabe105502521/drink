package com.example.gabe;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class fetchData extends AsyncTask<Void, Void, Void> {
    private String data;
    public static List<drinkshop> shops = new ArrayList<drinkshop>();
    public static int len =0;
    public fetchData(){};
    private Context mContext;
    public fetchData (Context context) {
        mContext = context;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //URL url =  new URL("https://api.myjson.com/bins/189646");
           URL url =  new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+MainActivity.location.getLatitude()+", "+MainActivity.location.getLongitude()+"&radius=1000&keyword="+URLEncoder.encode("飲料")+ "&key=AIzaSyBMum64_lpZuX7_M0ua4Mwc8aqz3CyArLI");
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

        Double mlng = 0.0, mlat = 0.0;
        mlng = MainActivity.location.getLongitude();
        mlat = MainActivity.location.getLatitude();


        //location == null 會造成程式crush,
        String crappyPrefix = "null";
        if (data.startsWith(crappyPrefix)) {
            data = data.substring(crappyPrefix.length(), data.length());
        } //報錯加的Value null of type org.json.JSONObject$1 cannot be converted to JSONObject
        try {
            //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONObject jsonObject = new JSONObject(data);
            JSONArray array = jsonObject.getJSONArray("results");
            len = array.length();
            for (int i = 0; i < len; i++) {
                JSONObject jsonObject2 = array.getJSONObject(i);

                //get lat, lng
                String geometry = jsonObject2.getString("geometry");
                JSONObject geo_jsonObject = new JSONObject(geometry);
                String location = geo_jsonObject.getString("location");
                JSONObject loca_jsonObject = new JSONObject(location);
                String lat = loca_jsonObject.getString("lat");
                String lng = loca_jsonObject.getString("lng");
                //get name、vicinity、ratin
                String name = jsonObject2.getString("name");
                String vicinity = jsonObject2.getString("vicinity");
                String rating = jsonObject2.getString("rating");
                String distance = Double.toString(gps2m(mlat, mlng, Double.valueOf(lat),Double.valueOf(lng)));
                drinkshop shop = new drinkshop(name, Double.parseDouble(lat), Double.parseDouble(lng), vicinity, Double.parseDouble(rating), distance);
                shops.add(shop);
            }
        } catch (JSONException e) {
            //Log.e(ListActivity.Tag, Log.getStackTraceString(e));
        }
        //sort
        sortRating(shops);

        //開始讀資料
        AssetManager assetManager = mContext.getAssets();
        InputStream inputStream = null;
        HashMap<String , String> drinks = new HashMap<>();
        String[] fileNames = null;
        try {
            fileNames = mContext.getAssets().list("shops"); //不能含"/"
            //Toast.makeText(mContext, ""+fileNames.length, Toast.LENGTH_SHORT).show();

            for(int i = 0; i < fileNames.length; i++){
                inputStream = assetManager.open("shops/"+fileNames[i]);
                String text = loadTextFile(inputStream);
                BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
                String line, tmp;
//                Toast.makeText(mContext, fileNames[i].substring(0,fileNames[i].length()-4), Toast.LENGTH_SHORT).show();

                while ( (line = br.readLine()) != null ) {
                    if(!line.trim().equals("")) {
                        String[] ss = line.split("\\s+");
                        drinks.put(ss[0], ss[1]);

                    }
                }
                for(int j = 0; j < len; j++){
                    if(fetchData.shops.get(j).name.equals(fileNames[i].substring(0,fileNames[i].length()-4))){

                        //Toast.makeText(mContext, "有嗎", Toast.LENGTH_SHORT).show();
                        fetchData.shops.get(j).drinks = drinks;
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //sort by rating
    public static void sortRating(List<drinkshop> list){
        Collections.sort(list);//编译通过；
    }
    //讀檔
    private String loadTextFile(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[4096];
        int len = 0;
        while ((len = inputStream.read(bytes)) > 0) {
            byteArrayOutputStream.write(bytes, 0, len);
        }
        return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
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
}
