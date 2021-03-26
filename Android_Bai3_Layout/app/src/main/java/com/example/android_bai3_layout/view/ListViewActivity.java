package com.example.android_bai3_layout.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android_bai3_layout.R;
import com.example.android_bai3_layout.adapter.FruitAdapter;
import com.example.android_bai3_layout.model.Fruit;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {


    ArrayList<Fruit> fruits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view2);
        ListView listViewFruit = findViewById(R.id.listViewFruit);

        fruits = new ArrayList<>();
        fruits.add(new Fruit("Apple","Táo", R.drawable.tao));
        fruits.add(new Fruit("Grape", "Nho",R.drawable.nho));
        fruits.add(new Fruit("Orange", "Cam",R.drawable.cam));
        fruits.add(new Fruit("Guava", "Ổi",R.drawable.oi));
        fruits.add(new Fruit("Watermelon", "Dưa hấu",R.drawable.duahau));
        fruits.add(new Fruit("Strawberry", "Dâu",R.drawable.dau));
        fruits.add(new Fruit("Pineapple","Dứa",R.drawable.dua));
        FruitAdapter adapter = new FruitAdapter(this, R.layout.list_item, fruits);
        listViewFruit.setAdapter(adapter);

        listViewFruit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this, position + " ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}