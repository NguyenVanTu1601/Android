package com.example.android_charts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChart_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart_);
        BarChart barChart = findViewById(R.id.barChar);

        ArrayList<BarEntry> visitor = new ArrayList<>();
        visitor.add(new BarEntry(2014,420));
        visitor.add(new BarEntry(2015,475));
        visitor.add(new BarEntry(2016,490));
        visitor.add(new BarEntry(2017,588));
        visitor.add(new BarEntry(2018,560));
        visitor.add(new BarEntry(2019,450));
        visitor.add(new BarEntry(2020,410));

        int color[] = {1,2,0,1,5};
        BarDataSet barDataSet = new BarDataSet(visitor, "Visitor");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Char Example");
        barChart.animateY(2000); // tốc độ hiện biểu đồ
    }
}
