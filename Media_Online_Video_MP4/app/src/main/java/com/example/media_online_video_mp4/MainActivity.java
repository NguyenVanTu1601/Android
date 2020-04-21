package com.example.media_online_video_mp4;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    Button btnMp4;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMp4 = findViewById(R.id.button);
        videoView = findViewById(R.id.videoView);
        String url = "https://khoapham.vn/download/vuoncaovietnam.mp4";
        btnMp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.radiantmediaplayer.com/media/bbb-360p.mp4");
                videoView.setVideoURI(uri);
                videoView.setMediaController(new MediaController(MainActivity.this));
                videoView.start();
            }
        });
    }
}
