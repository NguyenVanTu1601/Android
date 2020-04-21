package com.example.swipe_close_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * https://github.com/r0adkll/Slidr
 * Version 19 mới dùng được
 * Sửa manifes thêm phần theme vào, sửa style
 * Activity2 cần thêm backgroud trog linear layout
 */
public class MainActivity extends AppCompatActivity {
    Button btnOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpen = findViewById(R.id.buttonOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }
    private void openActivity2(){
        Intent intent = new Intent(MainActivity.this, Activity_two.class);
        startActivity(intent);
    }
}
