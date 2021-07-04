package com.example.android_b07_test.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_b07_test.R;
import com.example.android_b07_test.adapter.FoodPagerAdapter;
import com.example.android_b07_test.databinding.FragmentFoodBinding;

public class FoodFragment extends Fragment {


    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentFoodBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFoodBinding.inflate(inflater,container,false);

        FoodPagerAdapter adapter = new FoodPagerAdapter(getChildFragmentManager(),
                FoodPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPagerFood.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPagerFood);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}