package com.example.media_local_sound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnMp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMp3 = findViewById(R.id.button);
        btnMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;
                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.hon_ca_yeu);
                mediaPlayer.start();
            }
        });
    }
}
