package com.example.android_b07_test.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.android_b07_test.fragment.FastFoodFragment;
import com.example.android_b07_test.fragment.FruitFragment;
import com.example.android_b07_test.fragment.SnackFoodFragment;

public class FoodPagerAdapter extends FragmentStatePagerAdapter {
    int page_num = 3;
    public FoodPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1 : return new FastFoodFragment();
            case 2 : return new FruitFragment();
            default: return new SnackFoodFragment();
        }
    }

    @Override
    public int getCount() {
        return page_num;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 1: return "FastFood";
            case 2 : return "Fruit";
            default: return "Snack";
        }
    }
}
