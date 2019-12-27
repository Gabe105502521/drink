package com.example.gabe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class drinkActivity extends AppCompatActivity {
    //public String[] str = {"原鄉四季 M 25 L 30","特級綠茶 M 25 L 30","錫蘭奶紅 M 40 L 50","特級奶綠 M 40 L 50","珍珠奶茶 M 40 L 50","優多慮茶 M 40 L 45","冬瓜茶 M 25  L 30","檸檬汁 M 45 L 55","檸檬汁 M 45 L 55 ","冰淇淋紅茶 M 40 L 50","薑母茶 M 30 L 50"};
    //
    private ArrayAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_drink);

        //TextView tv = (TextView) findViewById(R.id.tv);
        ListView listview = (ListView) findViewById(R.id.drinkList);
        ArrayAdapter adapter = new ArrayAdapter<String>(drinkActivity.this,
                android.R.layout.simple_list_item_1
                );
        listview.setAdapter(adapter);

        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("text.txt");
            String text = loadTextFile(inputStream);
            //tv.setText(text);
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
            String line, tmp;

            while ( (line = br.readLine()) != null ) {
                if(!line.trim().equals("")){
                    String[] ss = line.split("\\s");
                    tmp  = ss[0] + "\nL  " + ss[1];
                    adapter.add(tmp);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*ListView listview = (ListView) findViewById(R.id.drinkList);
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        ArrayAdapter adapter = new ArrayAdapter<String>(drinkActivity.this,
                android.R.layout.simple_list_item_1,
                );
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onClickListView);       //指定事件 Method*/
    }


    private String loadTextFile(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[4096];
        int len = 0;
        while ((len = inputStream.read(bytes)) > 0) {
            byteArrayOutputStream.write(bytes, 0, len);
        }
        return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
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
