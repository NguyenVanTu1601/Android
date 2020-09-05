package com.example.progressbar_custom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int progr = 0;
    private Button btnIncr, btnDecr;
    private ProgressBar progressBar;
    private TextView textViewProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIncr = findViewById(R.id.button_incr);
        btnDecr = findViewById(R.id.button_decr);
        progressBar = findViewById(R.id.progess_bar);
        textViewProgress = findViewById(R.id.text_view_progess);

        updateProgressBar(progr);

        btnDecr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progr >= 10) progr -= 10;
                updateProgressBar(progr);
            }
        });
        btnIncr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progr <= 90) progr += 10;
                updateProgressBar(progr);
            }
        });


    }

    private void updateProgressBar(int progr){
        progressBar.setProgress(progr);
        textViewProgress.setText(progr + "%");
    }
}