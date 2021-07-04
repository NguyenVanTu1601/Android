package com.example.android_b07_bottomnavigation.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.android_b07_bottomnavigation.fragment.CuliFragment;
import com.example.android_b07_bottomnavigation.fragment.MokaFragment;
import com.example.android_b07_bottomnavigation.fragment.RobustaFragment;

public class CoffeeAdapter  extends FragmentStatePagerAdapter {
    public CoffeeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1: return new CuliFragment();
            case 2:return new RobustaFragment();
            default: return new MokaFragment();

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 1 : return "Culi";
            case 2 : return "Robusta";
            default: return "Moka";
        }
    }
}
