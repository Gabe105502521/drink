package com.example.gabe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("搜尋飲料");
        final EditText edt_input = findViewById(R.id.edt_input);
        Button btn_submit = findViewById(R.id.btn_submit);
        ListView listView = (ListView) findViewById(R.id.list);
        //edt_input.setText("   ");
        final String[] tmp = {""};
        final String shopsName[] = new String[fetchData.len];
        final String shopsRating[] = new String[fetchData.len];
        final List<HashMap<String, String>> list = new ArrayList<>();
        final HashMap<String, Integer> hashMap = new HashMap<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(search.this,
                android.R.layout.simple_list_item_1
        );
        listView.setAdapter(adapter);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!("".equals(edt_input.getText().toString().trim()))) {
                    for (int i = 0; i < fetchData.len; i++) {
                        if( fetchData.shops.get(i).drinks != null) {
                            for (Map.Entry<String, String> entry : fetchData.shops.get(i).drinks.entrySet()) {
                                if (entry.getKey().contains(edt_input.getText().toString().trim())) {
                                    hashMap.put(entry.getKey() + "\n" + fetchData.shops.get(i).name, Integer.valueOf(entry.getValue()));
                                    //adapter.add(entry.getKey() + "\n" + fetchData.shops.get(i).name + "  $" + Integer.valueOf(entry.getValue()));
                                }
                            }
                        }
                    }
                    List<Map.Entry<String, Integer>> list_Data =
                            new ArrayList<Map.Entry<String, Integer>>(hashMap.entrySet());
                    Collections.sort(list_Data, new Comparator<Map.Entry<String, Integer>>(){
                        public int compare(Map.Entry<String, Integer> entry1,
                                           Map.Entry<String, Integer> entry2){
                            return (entry1.getValue()-entry2.getValue());
                        }
                    });
                    for (Map.Entry<String, Integer> entry:list_Data) {
                        adapter.add(entry.getKey()+" " + "$" + entry.getValue());
                    }
                }
            }
        });
        listView.setOnItemClickListener(onClickListView);
    }

    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(search.this,choiceActivity.class);
            //intent.putExtra("position", position);
            search.this.startActivity(intent);
        }

    };
}
