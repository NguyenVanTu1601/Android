package com.example.android_b07_test.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_b07_test.R;
import com.example.android_b07_test.adapter.ImageAdapter;
import com.example.android_b07_test.databinding.FragmentDrinkBinding;
import com.example.android_b07_test.databinding.FragmentFastFoodBinding;

import java.util.ArrayList;
import java.util.List;

public class FastFoodFragment extends Fragment {
    public static FastFoodFragment newInstance(String param1, String param2) {
        FastFoodFragment fragment = new FastFoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentFastFoodBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFastFoodBinding.inflate(inflater, container, false);
        List<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.chicken);
        listImage.add(R.drawable.chicken2);
        listImage.add(R.drawable.hamburger);
        listImage.add(R.drawable.pizaa_thapcam);
        listImage.add(R.drawable.pizza_haisanjpg);
        listImage.add(R.drawable.pizza_xucxich);
        ImageAdapter adapter = new ImageAdapter(listImage,R.layout.item_layout,getContext());
        binding.listViewFastFood.setAdapter(adapter);

        return binding.getRoot();
    }
}