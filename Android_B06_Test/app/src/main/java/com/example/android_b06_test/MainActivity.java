package com.example.android_b06_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import com.example.android_b06_test.adapter.ViewPagerAdapter;
import com.example.android_b06_test.model.Music;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private List<Music> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        musicList = new ArrayList<>();
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position){
                    case 0:
                        tab.setIcon(R.drawable.ic_add);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.teal_200), PorterDuff.Mode.SRC_IN);
                        break;
                    case 1 :
                        tab.setIcon(R.drawable.ic_search);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.teal_200), PorterDuff.Mode.SRC_IN);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        tab.setIcon(R.drawable.ic_add);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.teal_700), PorterDuff.Mode.SRC_IN);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_search);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.teal_700), PorterDuff.Mode.SRC_IN);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setMusicList(List<Music> musicList){
        this.musicList = musicList;
    }

    public List<Music> getMusicList(){
        return musicList;
    }
}