package com.example.service_music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.buttonStart);
        btnStop = findViewById(R.id.buttonStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartService();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopService();
            }
        });
    }

    private void StartService(){
        Intent intentStart = new Intent(MainActivity.this, MusicService.class);
        this.startService(intentStart);
    }

    private void StopService(){
        Intent intentStop = new Intent(MainActivity.this, MusicService.class);
        this.stopService(intentStop);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StopService();
    }
}