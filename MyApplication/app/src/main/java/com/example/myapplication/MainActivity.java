package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int nam = 2019;
        String ten = "Nguyễn Văn Tú";
        Log.d("Khoahoc" ,ten + " " + nam);
        for(int i = 0; i < 5; i++){
            Thongbao();
        }
    }
    private void Thongbao(){
        Log.d("Khoahoc","Xin chao Tu Nguyen");
    }
}
