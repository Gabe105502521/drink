package com.example.gabe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class makerActivity extends AppCompatActivity {
    private String[] type = new String[] {"茶類", "加料"};
    private String[] tea = new String[]{"紅茶","綠茶","烏龍", "牛奶"};
    private String[] amount = new String[]{"5%","10%","30%", "50%"};
    private String[][] type2 = new String[][]{{"紅茶","綠茶","烏龍", "牛奶"},{"珍珠", "椰果", "波霸", "仙草"}};
    private String[][] type3 = new String[][]{{"5%","10%","30%", "50%"},{"1份", "2份", "3份", "4份"}};
    private Spinner spinner1;//第一個下拉選單
    private Spinner spinner2;//第二個下拉選單
    private Spinner spinner3;//第三個下拉選單
    ArrayAdapter<String> adapter ;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;

    private ListView list_maker;
    private ArrayAdapter adapter4;
    ArrayList<String> ar = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker);

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);

        Button btn_join = findViewById(R.id.btn_join);
        Button btn_clear = findViewById(R.id.btn_clear);
        Button btn_submit = findViewById(R.id.btn_submit);

        list_maker = (ListView) findViewById(R.id.list_maker);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                type);
        spinner1.setAdapter(adapter);
        adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                tea);
        spinner2.setAdapter(adapter2);
        adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                amount);
        spinner3.setAdapter(adapter3);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //Toast.makeText(makerActivity.this, "您選擇了:"+ type[position], Toast.LENGTH_SHORT).show();
                adapter2 = new ArrayAdapter<String>(makerActivity.this,android.R.layout.simple_spinner_item, type2[pos]);
                //載入第二個下拉選單Spinner
                spinner2.setAdapter(adapter2);

                adapter3 = new ArrayAdapter<String>(makerActivity.this,android.R.layout.simple_spinner_item, type3[pos]);
                //載入第二個下拉選單Spinner
                spinner3.setAdapter(adapter3);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        adapter4 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        list_maker.setAdapter(adapter4);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "";
                s = s + spinner2.getSelectedItem().toString()+spinner3.getSelectedItem().toString();
                //Toast.makeText(makerActivity.this, s, Toast.LENGTH_SHORT).show();
                adapter4.add(s);
            }
        });
        //清空
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter4.clear();
            }
        });
        //送出
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(makerActivity.this, "已收到訂單", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

}
