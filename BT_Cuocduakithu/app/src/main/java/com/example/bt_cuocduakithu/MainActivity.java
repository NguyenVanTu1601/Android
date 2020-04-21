package com.example.bt_cuocduakithu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView score;
    CheckBox cpone, cptwo, cpthree;
    SeekBar skone, sktwo, skthree;
    ImageButton iPlay;
    int Mark = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        DisableSeekBar();
        score.setText(Mark + "");
        final CountDownTimer countDownTimer = new CountDownTimer(60000,300) {
            @Override
            public void onTick(long l) {
                Random rd = new Random();
                // Dừng không cho chạy tiếp , kiểm tra phải 1 win và cược đúng k
                if(skone.getProgress() >= skone.getMax()) {
                    this.cancel();
                    iPlay.setVisibility(View.VISIBLE); // Hiện button để chơi tiếp
                    Toast.makeText(MainActivity.this, "One win", Toast.LENGTH_SHORT).show();
                    if(cpone.isChecked()) Mark += 10;
                    else Mark -= 10;
                    if(Mark <= 0) {
                        Toast.makeText(MainActivity.this, "Bạn đã thua cuộc", Toast.LENGTH_SHORT).show();
                        iPlay.setVisibility(View.INVISIBLE);
                    }
                    score.setText(Mark+"");
                    EnableCheckBox();
                }
                // Ktra 2
                if(sktwo.getProgress() >= sktwo.getMax()) {
                    this.cancel();
                    iPlay.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Two win", Toast.LENGTH_SHORT).show();
                    if(cptwo.isChecked()) Mark += 10;
                    else Mark -= 10;
                    if(Mark <= 0){
                        Toast.makeText(MainActivity.this, "Bạn đã thua cuộc", Toast.LENGTH_SHORT).show();
                        iPlay.setVisibility(View.INVISIBLE);
                    }
                    score.setText(Mark+"");
                    EnableCheckBox();
                }
                // Ktra 3
                if(skthree.getProgress() >= skthree.getMax()){
                    this.cancel();
                    iPlay.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Three win", Toast.LENGTH_SHORT).show();
                    if(cpthree.isChecked()) Mark += 10;
                    else Mark -= 10;
                    if(Mark <= 0){
                        Toast.makeText(MainActivity.this, "Bạn đã thua cuộc", Toast.LENGTH_SHORT).show();
                        iPlay.setVisibility(View.INVISIBLE);
                    }
                    score.setText(Mark+"");
                    EnableCheckBox();
                }

                skone.setProgress(skone.getProgress() + 1 + rd.nextInt(4));
                sktwo.setProgress(sktwo.getProgress() + 1 + rd.nextInt(4));
                skthree.setProgress(skthree.getProgress() + 1 + rd.nextInt(4));

            }

            @Override
            public void onFinish() {
                cpone.setChecked(false);
                cptwo.setChecked(false);
                cpthree.setChecked(false);
            }
        };
        iPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cpone.isChecked()||cptwo.isChecked() || cpthree.isChecked()){
                    skone.setProgress(0);
                    sktwo.setProgress(0);
                    skthree.setProgress(0);
                    iPlay.setVisibility(View.INVISIBLE); // ẩn button
                    countDownTimer.start();
                    DisableCheckBox();
                }
                else{
                    Toast.makeText(MainActivity.this, "Bạn phải đặt cược trước khi chơi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xet check 1 trong 3
        cpone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cptwo.setChecked(false);
                    cpthree.setChecked(false);
                }
            }
        });
        cptwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cpone.setChecked(false);
                    cpthree.setChecked(false);
                }
            }
        });
        cpthree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cpone.setChecked(false);
                    cptwo.setChecked(false);
                }
            }
        });
    }

    //Cho tich check box
    private void EnableCheckBox(){
        cpone.setEnabled(true);
        cptwo.setEnabled(true);
        cpthree.setEnabled(true);
    }
    // Khong cho tích checkbox
    private void DisableCheckBox(){
        cpone.setEnabled(false);
        cptwo.setEnabled(false);
        cpthree.setEnabled(false);
    }
    private void DisableSeekBar(){
        skone.setEnabled(false);
        sktwo.setEnabled(false);
        skthree.setEnabled(false);
    }
    private void Anhxa(){
        score   = (TextView) findViewById(R.id.Score);
        iPlay   = (ImageButton) findViewById(R.id.Play);
        cpone   = (CheckBox) findViewById(R.id.checkBox1);
        cptwo   = (CheckBox) findViewById(R.id.checkBox2);
        cpthree = (CheckBox) findViewById(R.id.checkBox3);
        skone   = (SeekBar) findViewById(R.id.seekBarOne);
        sktwo   = (SeekBar) findViewById(R.id.seekBarTwo);
        skthree = (SeekBar) findViewById(R.id.seekBarThree);
    }
}
