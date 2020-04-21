package com.example.intent_explicit_truyen_nhan_array;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txt = (TextView)findViewById(R.id.textViewArray);
        Intent intent = getIntent();
        String[] aa = intent.getStringArrayExtra("array");
        txt.setText(Arrays.toString(aa));
    }
}
