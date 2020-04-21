package com.example.spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Spinner cơ bản
 * Các bước :
 * 1. Kéo thả và ánh xạ spinner vào layout
 * 2. Tạo arrayList
 * 3. Đổ arrayList vào arrayAdapter
 * 4. Đổ adapter vào spinner
 * Nhìn chung spinner cơ bản chính là 1 cái listView custom cũng như vậy
 */
public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    ArrayList<String> listSinhVien;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinnerDanhSach);
        listSinhVien = new ArrayList<>();
        AddData();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listSinhVien);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Tạo khoảng cách giữa các item
        adapter.setNotifyOnChange(true);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, listSinhVien.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void AddData(){
        listSinhVien.add("Nguyễn Văn Tú");
        listSinhVien.add("Phùng Đình Tùng");
        listSinhVien.add("Trịnh Đình Trung");
        listSinhVien.add("Hàn Nhật Tuấn");
        listSinhVien.add("Nguyễn Văn Tài");
        listSinhVien.add("Nguyễn Đức Nam");
        listSinhVien.add("Nguyễn Tiến Khánh");
    }
}
