package com.example.android_charts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Mục tiêu : Vẽ các dạng biểu đồ trong android
 * Cài đặt thư viện : Maven trong project và implemention trong app
 * Bài sử dụng 3 kiểu chart
 * + Bar Chart :
 * + Pie Chart :
 * + Radar Chart :
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonBarChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BarChart_Activity.class));
            }
        });

        findViewById(R.id.buttonPieChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PieChart_Activity.class));
            }
        });

        findViewById(R.id.buttonRadarChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RadarChart_Ativity.class));
            }
        });
    }
}
