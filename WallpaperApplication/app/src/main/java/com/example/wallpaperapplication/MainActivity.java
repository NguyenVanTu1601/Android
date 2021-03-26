package com.example.wallpaperapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.os.Bundle;

import com.example.wallpaperapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<DataImage> imageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageList = new ArrayList<>();
        getListImage();
        ImagerDataAdapter adapter = new ImagerDataAdapter(imageList, this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        binding.recyclerView.setAdapter(adapter);
    }

    public void getListImage(){

        imageList.add(new DataImage(R.drawable.image1thumb, R.drawable.image1,"Image 1"));
        imageList.add(new DataImage(R.drawable.image2thumb, R.drawable.image2,"Image 2"));
        imageList.add(new DataImage(R.drawable.image3thumb, R.drawable.image3,"Image 3"));
        imageList.add(new DataImage(R.drawable.image4thumb, R.drawable.image4,"Image 4"));
        imageList.add(new DataImage(R.drawable.image5thumb, R.drawable.image5,"Image 5"));
    }
}