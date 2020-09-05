package com.example.progessbar_number;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daimajia.numberprogressbar.NumberProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final NumberProgressBar numberProgressBar = findViewById(R.id.number_progessbar);
        final EditText edt = findViewById(R.id.editText);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberProgressBar.setProgress(Integer.parseInt(edt.getText().toString()));
                numberProgressBar.setReachedBarColor(Color.parseColor("#FFFFFF"));
            }
        });
    }
}