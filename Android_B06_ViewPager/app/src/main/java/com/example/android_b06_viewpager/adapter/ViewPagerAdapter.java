package com.example.android_b06_viewpager.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.android_b06_viewpager.fragment.HomeFragment;
import com.example.android_b06_viewpager.fragment.NotifiFragment;
import com.example.android_b06_viewpager.fragment.SearchFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numPager = 3;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new NotifiFragment();
            case 2:
                return new SearchFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return numPager;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 1:
                return "Notification";
            case 2:
                return "Search";
            default:
                return "Home";
        }
    }
}
