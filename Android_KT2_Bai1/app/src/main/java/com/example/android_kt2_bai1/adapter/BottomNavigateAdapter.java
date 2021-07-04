package com.example.android_kt2_bai1.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.android_kt2_bai1.fragment.CuliCoffeeFragment;
import com.example.android_kt2_bai1.fragment.MokaCoffeeFragment;
import com.example.android_kt2_bai1.fragment.RobustaCoffeFragment;

public class BottomNavigateAdapter extends FragmentStatePagerAdapter {
    private int num_page = 3;

    public BottomNavigateAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new MokaCoffeeFragment();
            case 2 :
                return new RobustaCoffeFragment();
            default:
                return new CuliCoffeeFragment();
        }
    }

    @Override
    public int getCount() {
        return num_page;
    }
}
