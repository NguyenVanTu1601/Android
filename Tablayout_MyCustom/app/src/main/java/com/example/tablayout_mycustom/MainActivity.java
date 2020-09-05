package com.example.tablayout_mycustom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mProfileLabel, mUserLabel, mNotificationLabel;
    private ViewPager mMainPager;
    private PagerViewAdapter mPagerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfileLabel = findViewById(R.id.profileLabel);
        mUserLabel = findViewById(R.id.userLabel);
        mNotificationLabel = findViewById(R.id.NotifiLabel);

        mMainPager = findViewById(R.id.mainPager);

        mPagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mMainPager.setAdapter(mPagerViewAdapter);

        mProfileLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(0);
            }
        });

        mUserLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(1);
            }
        });

        mNotificationLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(2);
            }
        });

        mMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                changeTabs(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void changeTabs(int position) {
        if (position == 0){
            mProfileLabel.setTextColor(Color.parseColor("#FFB888"));
            mProfileLabel.setTextSize(22);

            mUserLabel.setTextColor(Color.parseColor("#FFFFFF"));
            mUserLabel.setTextSize(16);

            mNotificationLabel.setTextColor(Color.parseColor("#FFFFFF"));
            mNotificationLabel.setTextSize(16);
        }

        if (position == 1){
            mProfileLabel.setTextColor(Color.parseColor("#FFFFFF"));
            mProfileLabel.setTextSize(16);

            mUserLabel.setTextColor(Color.parseColor("#FFB888"));
            mUserLabel.setTextSize(22);

            mNotificationLabel.setTextColor(Color.parseColor("#FFFFFF"));
            mNotificationLabel.setTextSize(16);
        }

        if (position == 2){
            mProfileLabel.setTextColor(Color.parseColor("#FFFFFF"));
            mProfileLabel.setTextSize(16);

            mUserLabel.setTextColor(Color.parseColor("#FFFFFF"));
            mUserLabel.setTextSize(16);

            mNotificationLabel.setTextColor(Color.parseColor("#FFB888"));
            mNotificationLabel.setTextSize(22);
        }
    }
}