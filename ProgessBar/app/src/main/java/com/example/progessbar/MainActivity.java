package com.example.progessbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 10000 <-> 10s = tổng thời gian đếm ngược
                // 1000 -> thòi gian lặp thao tavs đếm
                CountDownTimer countDownTimer = new CountDownTimer(10000, 500) {
                    @Override
                    public void onTick(long l) {
                        int curren = progressBar.getProgress();
                        if(curren >= progressBar.getMax()){
                            curren = 0;
                        }
                        progressBar.setProgress(curren + 10);
                    }

                    @Override
                    public void onFinish() {
                        // this.start(); // gọi chính nó tạo vòng chạy vô hạn
                        Toast.makeText(MainActivity.this, "Hết giờ", Toast.LENGTH_SHORT).show();
                    }
                };
                countDownTimer.start();
            }
        });
    }
}
