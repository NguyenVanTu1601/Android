package com.example.listview_custom_adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Fruits> fruits;
    fruit_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anh_xa();
        adapter = new fruit_adapter(this, R.layout.line_item, fruits);
        listView.setAdapter(adapter);
    }
    private void Anh_xa(){
        listView = (ListView) findViewById(R.id.listViewFruits);
        fruits = new ArrayList<>();
        fruits.add(new Fruits("Apple","Táo", R.drawable.tao));
        fruits.add(new Fruits("Grape", "Nho",R.drawable.nho));
        fruits.add(new Fruits("Orange", "Cam",R.drawable.cam));
        fruits.add(new Fruits("Guava", "Ổi",R.drawable.oi));
        fruits.add(new Fruits("Watermelon", "Dưa hấu",R.drawable.duahau));
        fruits.add(new Fruits("Strawberry", "Dâu",R.drawable.dau));
        fruits.add(new Fruits("Pineapple","Dứa",R.drawable.dua));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, fruits.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
