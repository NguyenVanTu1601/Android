package com.example.android_b07_test.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_b07_test.R;
import com.example.android_b07_test.adapter.FoodPagerAdapter;
import com.example.android_b07_test.adapter.ImageAdapter;
import com.example.android_b07_test.databinding.FragmentDrinkBinding;

import java.util.ArrayList;
import java.util.List;


public class DrinkFragment extends Fragment {

    public static DrinkFragment newInstance(String param1, String param2) {
        DrinkFragment fragment = new DrinkFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentDrinkBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDrinkBinding.inflate(inflater, container, false);
        List<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.fansta);
        listImage.add(R.drawable.coca);
        listImage.add(R.drawable.sevenup);
        listImage.add(R.drawable.sting);
        listImage.add(R.drawable.bia);
        ImageAdapter adapter = new ImageAdapter(listImage,R.layout.item_layout,getContext());
        binding.listDrink.setAdapter(adapter);

        return binding.getRoot();
    }
}