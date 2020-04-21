package com.example.intent_explicit_truyen_nhan_dulieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView kq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        kq = (TextView) findViewById(R.id.textViewKq);
        // Nhan du lieu tu intent
        Intent intent = getIntent();
        String noidung = intent.getStringExtra("dulieu"); // ten chuoi gui di
        kq.setText(noidung);
    }
}
