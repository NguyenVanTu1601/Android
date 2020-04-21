package com.example.app_stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    TextView txtTime, txtNoiDung;
    Button btnStart, btnPause;
    int soLanBam = 0;
    StringBuilder content;
    int Time = 0;
    SeekBar seekBar;
    int lanthu = 1;
    Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        seekBar.setMax(100);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountTimer();
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                int soGiay = Time * 1000;
                txtNoiDung.append("Lần thứ " + lanthu + " : " + simpleDateFormat.format(soGiay) + "\n");
                lanthu++;

                handler.removeCallbacks(runnable); // tạm dừng handler
                Time = 0;
            }
        });
    }

    private void Anhxa() {
        txtNoiDung  = findViewById(R.id.textViewNoiDung);
        txtTime     = findViewById(R.id.textViewTime);
        btnStart    = findViewById(R.id.button);
        seekBar     = findViewById(R.id.seekBar);
        btnPause    = findViewById(R.id.buttonPause);
    }


    private void CountTimer(){
         handler = new Handler();
         runnable = new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                Time++;
                seekBar.setProgress(Time);
                int giay = Time * 1000;
                txtTime.setText(format.format(giay) + "");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.postDelayed(this,0);
            }
        };
        handler.postDelayed(runnable,0);
    }
}
