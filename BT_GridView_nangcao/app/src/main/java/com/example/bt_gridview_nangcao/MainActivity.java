package com.example.bt_gridview_nangcao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Weather> arrayList ;
    weather_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anh_xa();
        adapter = new weather_Adapter(this, arrayList,R.layout.line_weather);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, arrayList.get(i).getTen(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Anh_xa(){
        gridView = (GridView) findViewById(R.id.grid_weather);
        arrayList = new ArrayList<>();
        arrayList.add(new Weather("Gió",R.drawable.gio));
        arrayList.add(new Weather("Lốc xoáy",R.drawable.loc_xoay));
        arrayList.add(new Weather("Mây",R.drawable.may));
        arrayList.add(new Weather("Mưa rào",R.drawable.mua_rao));
        arrayList.add(new Weather("Mưa nhỏ",R.drawable.muanho));
        arrayList.add(new Weather("Nắng nhẹ",R.drawable.nang_nhe));
        arrayList.add(new Weather("Sấm sét",R.drawable.set));
        arrayList.add(new Weather("Nắng",R.drawable.sunny));

    }
}
