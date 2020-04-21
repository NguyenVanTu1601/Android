package com.example.innet_explicit_truyen_nhan_doituong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txt = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        Sinhvien sv = (Sinhvien) intent.getSerializableExtra("Doituong");
        txt.setText(sv.toString());
    }
}
