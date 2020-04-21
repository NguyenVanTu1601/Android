package com.example.youtube_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener{
    // Lấy key từ ggAPI
    String API_key = "AIzaSyBTQKYtEOfGFTI5IqRAmMI5n0TGdWtNDHw";

    //Chèn libs yutubeAPI vào app
    YouTubePlayerView youTubePlayerView;
    int REQUEST_VIDEO = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        youTubePlayerView = findViewById(R.id.myYoutube);

        // kết nối tới GG API thông qua Key tại màn hình mainactivity.this
        youTubePlayerView.initialize(API_key,this);

    }

    //phương thức xử lý khi kết nối thành công
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        // Phát video có key = M9_.... là phần sau dấu = trên link http
        youTubePlayer.cueVideo("M9_3yP3KG-w");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        // Ngược lại đưa ra lỗi nếu ko kết nối được phía ng dùng
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(MainActivity.this, REQUEST_VIDEO);
        }else{
            Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
        }
    }

    // Nếu trả về lỗi, khởi tạo lại kết nối
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_VIDEO){
            youTubePlayerView.initialize(API_key,MainActivity.this);
        }
    }
}
