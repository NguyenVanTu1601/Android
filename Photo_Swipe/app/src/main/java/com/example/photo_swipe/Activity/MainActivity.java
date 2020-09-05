package com.example.photo_swipe.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.photo_swipe.Adapter.GalleryImageAdapter;
import com.example.photo_swipe.Interface.IRecyclerViewClickedListioner;
import com.example.photo_swipe.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this,2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Random random = new Random();
        final String[] images = new String[10];
        for (int i = 0; i < images.length; i++){
            images[i] = "https://picsum.photos/600?image=" + random.nextInt(600);
        }

        IRecyclerViewClickedListioner recyclerViewClickedListioner = new IRecyclerViewClickedListioner() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this,FullSceenActivity.class);
                intent.putExtra("IMAGES",images);
                intent.putExtra("POSITION", position);
                startActivity(intent);

            }
        };

        GalleryImageAdapter galleryImageAdapter = new GalleryImageAdapter(this,images,recyclerViewClickedListioner);
        recyclerView.setAdapter(galleryImageAdapter);


    }
}