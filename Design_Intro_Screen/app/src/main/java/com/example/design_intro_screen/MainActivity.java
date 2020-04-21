package com.example.design_intro_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * LinearLayout ko cho phép chèn cái nọ lên cái kia
 * Sử dụng contrain Layout hoặc RelatipLayout để làm , mọi thứ làm trong các file xml của drawble, xml Main
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
