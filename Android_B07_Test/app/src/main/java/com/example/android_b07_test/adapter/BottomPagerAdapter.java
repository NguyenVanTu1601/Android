package com.example.android_b07_test.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.android_b07_test.fragment.DrinkFragment;
import com.example.android_b07_test.fragment.FoodFragment;

public class BottomPagerAdapter extends FragmentStatePagerAdapter {
    int page_number = 2;
    public BottomPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1: return  new DrinkFragment();
            default: return new FoodFragment();
        }
    }

    @Override
    public int getCount() {
        return page_number;
    }

}
