package com.example.huong_doi_tuong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sinhvien sinhvien = new Sinhvien("phung dinh tung","Lao cai", 1999);
        sinhvien.setHoten("Nguyen Van Tu");
        Toast.makeText(this, sinhvien.getAddress(), Toast.LENGTH_LONG).show();
    }
}
