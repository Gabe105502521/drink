package com.example.gabe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    public static ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setTitle("附近飲料店");

        listView = findViewById(R.id.listView);
        List<HashMap<String, String>> list = new ArrayList<>();
        String shopsName[] = new String[fetchData.len];
        String shopsRating[] = new String[fetchData.len];
        String shopsDistance[] = new String[fetchData.len];
        for (int i=0; i< fetchData.len ; i++) {
            shopsName[i] = fetchData.shops.get(i).name;
            shopsRating[i] = fetchData.shops.get(i).rating.toString();
            shopsDistance[i] = fetchData.shops.get(i).distance;
        }
        for (int i = 0; i < fetchData.len; i++) {
            shopsRating[i] = fetchData.shops.get(i).rating.toString();
        }

        for(int i = 0 ; i < fetchData.len ; i++){
            HashMap<String , String> hashMap = new HashMap<>();
            hashMap.put("shopsName" , shopsName[i]);
            hashMap.put("shopsRating" , "評分: "+shopsRating[i]+"    距離: "+shopsDistance[i] + "公尺");
            //將shopsName , shopsRating存入HashMap之中
            list.add(hashMap);
            //把HashMap存入list之中
        }

        ListAdapter listAdapter = new SimpleAdapter(
                ListActivity.this,
                list,
                android.R.layout.simple_list_item_2 ,
                new String[]{"shopsName" , "shopsRating"} ,
                new int[]{android.R.id.text1 , android.R.id.text2});
        // 5個參數 : context , List , layout , key1 & key2 , text1 & text2
        ListActivity.listView.setAdapter(listAdapter);
        ListActivity.listView.setOnItemClickListener(onClickListView);

    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast 快顯功能 第三個參數 Toast.LENGTH_SHORT 2秒  LENGTH_LONG 5秒
            //Toast.makeText(mContext,"點選第 "+(position +1) +" 個 \n內容：",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ListActivity.this,drinkActivity.class);
            intent.putExtra("position", position);
            ListActivity.this.startActivity(intent);
        }
    };

}
