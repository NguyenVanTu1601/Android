package com.example.android_b07_test.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_b07_test.R;
import com.example.android_b07_test.adapter.ImageAdapter;
import com.example.android_b07_test.databinding.FragmentFastFoodBinding;
import com.example.android_b07_test.databinding.FragmentSnackFoodBinding;

import java.util.ArrayList;
import java.util.List;


public class SnackFoodFragment extends Fragment {

    public SnackFoodFragment() {
        // Required empty public constructor
    }

    public static SnackFoodFragment newInstance(String param1, String param2) {
        SnackFoodFragment fragment = new SnackFoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSnackFoodBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSnackFoodBinding.inflate(inflater, container, false);
        List<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.snack_bido);
        listImage.add(R.drawable.snack_ca);
        listImage.add(R.drawable.snack_khoaitay);
        listImage.add(R.drawable.snack_nani);
        listImage.add(R.drawable.snack_rongbien);
        ImageAdapter adapter = new ImageAdapter(listImage,R.layout.item_layout,getContext());
        binding.listSnack.setAdapter(adapter);

        return binding.getRoot();
    }
}