package com.example.bt_listview_custom_adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<weather> weatherArrayList;
    weather_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anh_xa();
        adapter = new weather_adapter(this, R.layout.item_line, weatherArrayList);
        listView.setAdapter(adapter);
    }
    private void Anh_xa(){
        weatherArrayList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewWeather);
        weatherArrayList.add(new weather(R.drawable.nang_nhe,"Hà Nội", "Nắng nhẹ", 28));
        weatherArrayList.add(new weather(R.drawable.cau_vong,"Thái Bình", "Cầu vồng", 24));
        weatherArrayList.add(new weather(R.drawable.may,"Lào Cai", "Nhiều mây", 20));
        weatherArrayList.add(new weather(R.drawable.gio,"Hồ Chí Minh", "Nhiều gió", 26));
        weatherArrayList.add(new weather(R.drawable.loc_xoay,"Thanh Hóa", "Lốc xoáy", 27));
        weatherArrayList.add(new weather(R.drawable.mua_rao,"Nghệ An", "Mưa rào", 22));
        weatherArrayList.add(new weather(R.drawable.muanho,"Hà Tĩnh", "Mưa nhỏ", 24));
        weatherArrayList.add(new weather(R.drawable.set,"Hà Nam", "Sấm sét", 25));
        weatherArrayList.add(new weather(R.drawable.sunny,"Cà Mau", "Nắng", 32));
        weatherArrayList.add(new weather(R.drawable.nang_nhe,"Hưng Yên", "Nắng nhẹ", 28));
    }
}
