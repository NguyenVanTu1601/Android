package com.example.android_b07_bottomnavigation.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.android_b07_bottomnavigation.fragment.CoffeeFragment;
import com.example.android_b07_bottomnavigation.fragment.HomeFragment;
import com.example.android_b07_bottomnavigation.fragment.NotificationFragment;
import com.example.android_b07_bottomnavigation.fragment.SearchFragment;

public class BottomAdapter extends FragmentStatePagerAdapter {

    private int numPager = 4;
    public BottomAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new SearchFragment();
            case 2 :
                return new NotificationFragment();
            case 3:
                return new CoffeeFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return numPager;
    }
}
