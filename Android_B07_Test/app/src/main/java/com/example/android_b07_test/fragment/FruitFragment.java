package com.example.android_b07_test.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_b07_test.R;
import com.example.android_b07_test.adapter.ImageAdapter;
import com.example.android_b07_test.databinding.FragmentFruitBinding;
import com.example.android_b07_test.databinding.FragmentSnackFoodBinding;

import java.util.ArrayList;
import java.util.List;

public class FruitFragment extends Fragment {

    public static FruitFragment newInstance(String param1, String param2) {
        FruitFragment fragment = new FruitFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentFruitBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFruitBinding.inflate(inflater, container, false);
        List<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.apple);
        listImage.add(R.drawable.banana);
        listImage.add(R.drawable.orange);
        listImage.add(R.drawable.strawbery);
        ImageAdapter adapter = new ImageAdapter(listImage,R.layout.item_layout,getContext());
        binding.listFruit.setAdapter(adapter);

        return binding.getRoot();
    }
}