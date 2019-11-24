package com.example.gabe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class random_Activity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter adapter;

    ArrayList<String> ar = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_);

        //Button btn_main = findViewById(R.id.btn_main);
        Button btn_submit = findViewById(R.id.btn_submit);
        Button btn_done = findViewById(R.id.btn_done);
        final EditText edt_input = findViewById(R.id.edt_input);

        listView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        /*btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!("".equals(edt_input.getText().toString().trim()))) {
                    adapter.add(edt_input.getText());
                    ar.add(edt_input.getText().toString());
                    edt_input.setText("");
                }
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(ar.size()>0) {
                    new AlertDialog.Builder(random_Activity.this)
                            .setTitle("買了啦")//設定視窗標題
                            .setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                            .setMessage(ar.get((int) (Math.random() * ar.size())))//設定顯示的文字
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })//設定結束的子視窗
                            .show();//呈現對話視窗
                }
            }
        });
    }
}
