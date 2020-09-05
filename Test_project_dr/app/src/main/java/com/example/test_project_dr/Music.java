package com.example.test_project_dr;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class Music extends Service {
    private String state = "";
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        {
            state = intent.getStringExtra("state");

            if(state.equals("ON")){
                mediaPlayer = MediaPlayer.create(this, R.raw.co_bao_gio);
                mediaPlayer.start();

            }else{
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

            }

            return START_STICKY;
        }
    }
}
