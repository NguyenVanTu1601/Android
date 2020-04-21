package com.example.windowmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

/**
 * Mục tiêu của project là tạo tính năng thu nhỏ khi ko dùng đến, giống messenger khi ko cần thì thu thành icon ở góc màn hình
 * Thao tác :
 * + Tạo service và làm việc hoàn toàn trên service
 * + MainActivity bỏ đi do đó làm trong suốt nó bằng thuộc tính theme
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, Messge_Service.class));
        finish(); // kết thúc thao tác trên activity
    }


}
