package com.example.android_b06_viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentManager;
import android.os.Bundle;

import com.example.android_b06_viewpager.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * thuộc tính tabMode dùng để chọn loại tab như scroll khi nhiều tab, fix, ...
 */
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT); // resume lại main khi main thay đổi vd cập nhật lại danh sách
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new Tranfomer());

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_notification);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_search);


    }
}