package com.example.gabe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class drinkActivity extends AppCompatActivity {
    public String[] str = {"原鄉四季 M 25 L 30","特級綠茶 M 25 L 30","錫蘭奶紅 M 40 L 50","特級奶綠 M 40 L 50","珍珠奶茶 M 40 L 50","優多慮茶 M 40 L 45","冬瓜茶 M 25  L 30","檸檬汁 M 45 L 55","檸檬汁 M 45 L 55 ","冰淇淋紅茶 M 40 L 50","薑母茶 M 30 L 50"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        ListView listview = (ListView) findViewById(R.id.drinkList);
        ArrayAdapter adapter = new ArrayAdapter<String>(drinkActivity.this,
                android.R.layout.simple_list_item_1,
                str);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onClickListView);       //指定事件 Method
    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            new AlertDialog.Builder(drinkActivity.this)
                    .setTitle("選大小")
                    .setPositiveButton("M", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(drinkActivity.this,"已加入購物車",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("L", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(drinkActivity.this,"已加入購物車",Toast.LENGTH_SHORT).show();
                }
            }).show();
        }

    };
}
