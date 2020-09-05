package com.example.scroll_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NonScrollListView listTaikhoan, listDuno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listTaikhoan = findViewById(R.id.listTaikhoan);
        listDuno = findViewById(R.id.listDuno);

        List<String> tk = new ArrayList<>();
        List<String> dn = new ArrayList<>();


        tk.add("Tú");
        tk.add("Tùng");
        tk.add("Trung");
        tk.add("Nam");
        tk.add("Việt");
        tk.add("Hải");
        tk.add("Hà");
        tk.add("Hạnh");
        tk.add("Hồng");
        tk.add("Hương");
        tk.add("Thành");
        tk.add("Anh");
        tk.add("Đan");
        tk.add("Đại");

        dn.add("Tú");
        dn.add("Tùng");
        dn.add("Trung");
        dn.add("Nam");
        dn.add("Việt");
        dn.add("Hải");
        dn.add("Hà");
        dn.add("Hạnh");
        dn.add("Hồng");
        dn.add("Hương");

        ArrayAdapter TKAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1 , tk);
        listTaikhoan.setAdapter(TKAdapter);

        ArrayAdapter DNAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1 , dn);
        listDuno.setAdapter(DNAdapter);

    }
}