package com.example.media_app_music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtTenBaihat, txtTime, txtTotalTime;
    ImageButton imgReturn, imgPlay, imgStop, imgNext;
    ImageView imgCD;
    SeekBar seekBarTime;
    ArrayList<Song> arraySong;
    MediaPlayer mediaPlayer;
    int position = 0;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_cd);
        Anhxa();
        AddSong();
        KhoiTaoMedia();
        setTotalTime();

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTotalTime();
                updateTimeSong();
                imgCD.startAnimation(animation);
                if(mediaPlayer.isPlaying()){
                    // Nếu đang phát nhạc
                    imgPlay.setImageResource(R.drawable.play);
                    mediaPlayer.pause();
                }else{
                    imgPlay.setImageResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });

        imgStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                imgPlay.setImageResource(R.drawable.play);
                KhoiTaoMedia();

            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                position++;
                if(position > arraySong.size() - 1){
                    position = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                KhoiTaoMedia();
                mediaPlayer.start();
                imgPlay.setImageResource(R.drawable.pause);
                setTotalTime();
                updateTimeSong();
            }
        });

        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position < 0){
                    position = arraySong.size() - 1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                KhoiTaoMedia();
                mediaPlayer.start();
                imgPlay.setImageResource(R.drawable.pause);
                setTotalTime();
                updateTimeSong();
            }
        });

        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Cập nhât trong quá trình kéo seekbar

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Khi vừa chạm
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Khi dừng chạm
                mediaPlayer.seekTo(seekBarTime.getProgress()); // nhảy đến đoạn kq của seekBar

            }
        });
    }
    private void Anhxa(){
        txtTenBaihat    = findViewById(R.id.textViewName);
        txtTime         = findViewById(R.id.textViewTime);
        txtTotalTime    = findViewById(R.id.textViewTotalTime);
        imgReturn       = findViewById(R.id.imageButtonReturn);
        imgNext         = findViewById(R.id.imageButtonNext);
        imgPlay         = findViewById(R.id.imageButtonPlay);
        imgStop         = findViewById(R.id.imageButtonStop);
        seekBarTime     = findViewById(R.id.seekBar);
        imgCD           = findViewById(R.id.imageViewCD);
    }
    private void AddSong(){
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Có bao giờ",R.raw.co_bao_gio));
        arraySong.add(new Song("Em dạo này", R.raw.em_dao_nay));
        arraySong.add(new Song("Hơn cả yêu", R.raw.hon_ca_yeu));
        arraySong.add(new Song("Là", R.raw.la));
        arraySong.add(new Song("Lạ lùng", R.raw.lalung));
        arraySong.add(new Song("Một phút", R.raw.mot_phut));
        arraySong.add(new Song("Sáng nay mưa", R.raw.sang_nay_mua));
        arraySong.add(new Song("Vy Vy cười rất đẹp", R.raw.yetcndt));

    }

    private void KhoiTaoMedia(){
        mediaPlayer = mediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        txtTenBaihat.setText(arraySong.get(position).getTenBaiHat());
    }

    private void setTotalTime(){
        int time = mediaPlayer.getDuration(); // trả về thời gian theo mls
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTime.setText(simpleDateFormat.format(time));
        // gán max của seekbar = media player
        seekBarTime.setMax(mediaPlayer.getDuration());
    }

    private void updateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                txtTime.setText(format.format(mediaPlayer.getCurrentPosition())); // Vị trí hiện tại đang phát
                // update seekBar
                seekBarTime.setProgress(mediaPlayer.getCurrentPosition());

                // Kiểm tra bài hát đã hết chưa->next bài mới
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position > arraySong.size() - 1){
                            position = 0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }

                        KhoiTaoMedia();
                        mediaPlayer.start();
                        imgPlay.setImageResource(R.drawable.pause);
                        setTotalTime();
                        updateTimeSong();
                    }
                });
                handler.postDelayed(this,500); // lặp lại handler
            }
        },100);
    }

}
