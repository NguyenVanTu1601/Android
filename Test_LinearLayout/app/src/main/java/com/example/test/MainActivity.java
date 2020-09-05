package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerViewTaikhoan = findViewById(R.id.recycler_Taikhoan);
        RecyclerView recyclerViewDuno = findViewById(R.id.recycler_Duno);
        recyclerViewDuno.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTaikhoan.setLayoutManager(new LinearLayoutManager(this));

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

        TestAdapter tkAdapter = new TestAdapter(tk, this);
        TestAdapter dnAdapter = new TestAdapter(dn, this);
        recyclerViewTaikhoan.setAdapter(tkAdapter);
        recyclerViewDuno.setAdapter(dnAdapter);

    }
}
