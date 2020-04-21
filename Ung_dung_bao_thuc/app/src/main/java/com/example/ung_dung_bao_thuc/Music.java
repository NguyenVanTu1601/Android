package com.example.ung_dung_bao_thuc;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Đã học vài service bên
 */
public class Music extends Service {
    // Khi tới giờ báo thức, pending sẽ gửi thông báo tới alamReciver, sau đó sẽ gửi thông báo tới Service
    MediaPlayer mediaPlayer;
    int key = 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // overide và sử dụng phương thức onStartCommand này
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String trang_thai = intent.getStringExtra("extra");
        if(trang_thai.equals("on")){
            key = 1;
        }else{
            key = 0;
        }

        if(key == 1){
            mediaPlayer = MediaPlayer.create(this, R.raw.co_bao_gio);
            mediaPlayer.start();
            key = 0;
        }else{
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer.release();
            }


        }

        return START_STICKY;
    }
}
