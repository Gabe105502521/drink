package com.example.gabe;

import android.os.AsyncTask;
import android.util.Log;

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
import java.util.List;

public class fetchData extends AsyncTask<Void, Void, Void> {
    String data;
    //JSONArray JArray=new JSONArray();
    //JSONObject jsonObj =new JSONObject();
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
        String content = null;
        ListActivity.tv.setText(data);
        String crappyPrefix = "null";

        if(data.startsWith(crappyPrefix)){
            data = data.substring(crappyPrefix.length(), data.length());
        } //報錯加的Value null of type org.json.JSONObject$1 cannot be converted to JSONObject
        try{
            //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONObject jsonObject = new JSONObject(data);
           /* if(jsonObject.has("results")){
                content = jsonObject.getString("results");
            }else{
                content="?";
            }*/
           JSONArray array = jsonObject.getJSONArray("results");
           for (int i = 0; i < array.length(); i++) {
               JSONObject jsonObject2 = array.getJSONObject(i);
               String geometry = jsonObject2.getString("geometry");
               if (geometry != null) {
                   ListActivity.tv.setText("!");
               }
               String name = jsonObject2.getString("name");
               if (name != null) {
                   ListActivity.tv.setText("!!");
               }
               String vicinity = jsonObject2.getString("vicinity");
               if (vicinity != null) {
                   ListActivity.tv.setText("!!!");
               }

           }
        }
        catch(JSONException e) {
            Log.e(ListActivity.Tag,Log.getStackTraceString(e));
        }
        //ListActivity.tv.setText(content);
    }
}
