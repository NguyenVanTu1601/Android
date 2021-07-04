package com.example.android_b07_bottomnavigation.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_b07_bottomnavigation.R;
import com.example.android_b07_bottomnavigation.adapter.CoffeeAdapter;
import com.google.android.material.tabs.TabLayout;

public class CoffeeFragment extends Fragment {

    public static CoffeeFragment newInstance(String param1, String param2) {
        CoffeeFragment fragment = new CoffeeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ViewPager viewPager;
    TabLayout tab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_coffee, container, false);
        CoffeeAdapter adapter = new CoffeeAdapter(getChildFragmentManager(), CoffeeAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager = view.findViewById(R.id.viewPagerCoffee);
        tab = view.findViewById(R.id.tabLayout);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
        return view;
    }
}