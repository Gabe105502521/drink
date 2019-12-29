package com.example.gabe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.Map;

public class drinkActivity extends AppCompatActivity {
    //public String[] str = {"原鄉四季 M 25 L 30","特級綠茶 M 25 L 30","錫蘭奶紅 M 40 L 50","特級奶綠 M 40 L 50","珍珠奶茶 M 40 L 50","優多慮茶 M 40 L 45","冬瓜茶 M 25  L 30","檸檬汁 M 45 L 55","檸檬汁 M 45 L 55 ","冰淇淋紅茶 M 40 L 50","薑母茶 M 30 L 50"};
    //
    private ArrayAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_drink);
        String s = "";
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);

        ListView listview = (ListView) findViewById(R.id.drinkList);
        ArrayAdapter adapter = new ArrayAdapter<String>(drinkActivity.this,
                android.R.layout.simple_list_item_1
        );
        listview.setAdapter(adapter);

        if(fetchData.shops.get(position).drinks != null) {
            for (Map.Entry<String, String> entry : fetchData.shops.get(position).drinks.entrySet()) {
                s = entry.getKey() + "\n" + " L  " + entry.getValue();
                adapter.add(s);
            }
        }
        listview.setOnItemClickListener(onClickListView);
    }


    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*new AlertDialog.Builder(drinkActivity.this)
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
            }).show();*/
            Intent intent = new Intent(drinkActivity.this,choiceActivity.class);
            //intent.putExtra("position", position);
            drinkActivity.this.startActivity(intent);
        }

    };
}
