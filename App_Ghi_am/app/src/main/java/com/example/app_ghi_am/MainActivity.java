package com.example.app_ghi_am;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

/**
 * Nhớ xin quyền
 * Vẫn bị sai @@
 */
public class MainActivity extends AppCompatActivity {
    Button btnStart, btnStop, btnPlay, btnStopPlay;
    private MediaRecorder myRecorder;
    private MediaPlayer myMediaplayer;
    private String outputFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ghiam.3gpp";
        myRecorder = new MediaRecorder();

        // Sai ở 3 dòng này nhưng k hiểu vì sao
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        myRecorder.setOutputFile(outputFile);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stop();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Play();
            }
        });

        btnStopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stop_Play();
            }
        });
    }

    private void Anhxa() {
        btnStart = findViewById(R.id.buttonStart);
        btnStop = findViewById(R.id.buttonStop);
        btnPlay = findViewById(R.id.buttonPlay);
        btnStopPlay = findViewById(R.id.buttonStopPlay);
    }

    public void Start() {
        if(myRecorder != null){
            try {
                myRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        myRecorder.start();
        Toast.makeText(getApplicationContext(),"Start recording...",Toast.LENGTH_SHORT).show();

    }

    public void Stop() {
        if(myRecorder != null){
            myRecorder.stop();
            myRecorder.release();
            myRecorder = null;
        }
        Toast.makeText(getApplicationContext(),"Stop recording...",Toast.LENGTH_SHORT);

    }

    public void Play() {

        try {
            myMediaplayer = new MediaPlayer();
            myMediaplayer.setDataSource(outputFile);
            myMediaplayer.prepareAsync();
            myMediaplayer.start();
            Toast.makeText(getApplicationContext(),"Start play the recording...",Toast.LENGTH_SHORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Stop_Play() {
        if(myMediaplayer != null){
            myMediaplayer.stop();
            myMediaplayer.release();
            myMediaplayer = null;
            Toast.makeText(getApplicationContext(),"Stop playing...",Toast.LENGTH_SHORT);
        }
    }
}
