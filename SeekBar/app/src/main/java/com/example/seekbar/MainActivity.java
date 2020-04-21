package com.example.seekbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBarSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBarSound = (SeekBar) findViewById(R.id.sound);
        seekBarSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("Thay doi", "Sound " + i);
                // i là giá trị trả về trên thanh seekbar
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("Bat dau", "Start!");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("Ket thuc", "Finish!");
            }
        });
    }
}
