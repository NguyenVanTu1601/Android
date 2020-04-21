package com.example.bt_background;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Random;
// Hiện ảnh nền background ngẫu nhiên từ danh sách ảnh
public class MainActivity extends AppCompatActivity {
    ConstraintLayout ctn;
    ArrayList<Integer> ImageBackground = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctn = (ConstraintLayout)findViewById(R.id.backg);
        ImageBackground.add(R.drawable.android_wallpaper);
        ImageBackground.add(R.drawable.crop);
        ImageBackground.add(R.drawable.wall1);
        ImageBackground.add(R.drawable.wall2);
        ImageBackground.add(R.drawable.wall3);
        ImageBackground.add(R.drawable.wall4);
        Random random = new Random();
        int image_ = random.nextInt(ImageBackground.size());
        ctn.setBackgroundResource(ImageBackground.get(image_));
    }
}
