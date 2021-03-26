package com.example.android_bai3_layout.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.android_bai3_layout.R;
import com.example.android_bai3_layout.adapter.FruitRvAdapter;
import com.example.android_bai3_layout.model.Fruit;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    ArrayList<Fruit> fruits;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerFruit);
        fruits = new ArrayList<>();
        fruits.add(new Fruit("Apple","Táo", R.drawable.tao));
        fruits.add(new Fruit("Grape", "Nho",R.drawable.nho));
        fruits.add(new Fruit("Orange", "Cam",R.drawable.cam));
        fruits.add(new Fruit("Guava", "Ổi",R.drawable.oi));
        fruits.add(new Fruit("Watermelon", "Dưa hấu",R.drawable.duahau));
        fruits.add(new Fruit("Strawberry", "Dâu",R.drawable.dau));
        fruits.add(new Fruit("Pineapple","Dứa",R.drawable.dua));

        FruitRvAdapter adapter = new FruitRvAdapter(fruits, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }

}
// Bài 3: + Bài 2+ kiểm tra dạng bài 2 bài 1 tính toán đơn giản