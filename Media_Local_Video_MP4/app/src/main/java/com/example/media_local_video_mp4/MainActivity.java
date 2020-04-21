package com.example.media_local_video_mp4;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    Button btnMp4;
    VideoView videoMP4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMp4 = findViewById(R.id.button);
        videoMP4 = findViewById(R.id.videoView);
        btnMp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoMP4.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.co_bao_gio));

                MediaController mediaController = new MediaController(MainActivity.this);
                mediaController.setMediaPlayer(videoMP4);
                videoMP4.setMediaController(mediaController);

                videoMP4.start();
            }
        });
    }
}
