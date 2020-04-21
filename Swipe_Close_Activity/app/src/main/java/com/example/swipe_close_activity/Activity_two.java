package com.example.swipe_close_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class Activity_two extends AppCompatActivity {
    Button btnLock, btnUnLock;
    private SlidrInterface slidr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        btnLock = findViewById(R.id.buttonLock);
        btnUnLock = findViewById(R.id.buttonUnLock);

        // Tạo slidr
        slidr = Slidr.attach(this);

        // Bắt sự kiện khóa, mở khóa
        btnUnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unLock(v);
            }
        });

        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockSlide(v);
            }
        });
    }
    private void lockSlide(View v){
        slidr.lock();
    }
    private void unLock(View v){
        slidr.unlock();
    }
}
