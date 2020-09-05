package com.example.photo_swipe.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.photo_swipe.Adapter.FullSizeAdapter;
import com.example.photo_swipe.R;

public class FullSceenActivity extends AppCompatActivity {

    ViewPager viewPager;
    int position;
    String[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_sceen);

        if (savedInstanceState == null){
            Intent intent = getIntent();
            position = intent.getIntExtra("POSITION", 0);
            images = intent.getStringArrayExtra("IMAGES");
        }

        viewPager = findViewById(R.id.viewPager);
        FullSizeAdapter fullSizeAdapter = new FullSizeAdapter(this, images);
        viewPager.setAdapter(fullSizeAdapter);
        viewPager.setCurrentItem(position,true);

    }
}