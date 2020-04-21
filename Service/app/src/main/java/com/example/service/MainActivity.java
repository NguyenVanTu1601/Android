package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Project này học về startService
 * Vòng đời  : onCreate() -> onStartCommand -> running ->onDestroy. Oncreate chỉ gọi 1 lần duy nhất khi khởi tại ctrinh
 *  + START_STICKY : khi servive chết thì tự khởi tạo lại nhưng  ko nên sử dụng vs các service truyền tin do khi khởi tạo lại nó truyền vào 1 intent null
 *                   cho dù trong code có truyền intent thông tin nào đó khác
 *  + START_NOT_STICKY : chết ko tạo lại
 *  + START_REDELIVER_INTENT : khôi phục service với trạng thái trước khi chết
 *
 */

/**
 * Bước 1 : Tạo class extends Service và khai báo service trong manifes
 * Bước 2. Quay lại Mainctivity tạo button start và stopService
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnStart, btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.startService);
        btnStop = findViewById(R.id.stopService);
        btnStop.setOnClickListener(this);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, StartService.class);
        switch (v.getId()){
            case R.id.startService:

                startService(intent);
                break;
            case R.id.stopService:
                stopService(intent);
                break;
        }
    }
}
