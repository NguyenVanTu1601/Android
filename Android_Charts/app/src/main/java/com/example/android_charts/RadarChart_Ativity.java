package com.example.android_charts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class RadarChart_Ativity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart__ativity);
        RadarChart radarChart = findViewById(R.id.radarChart);

        //Vesitor1
        ArrayList<RadarEntry> visitors = new ArrayList<>();
        visitors.add(new RadarEntry(420));
        visitors.add(new RadarEntry(470));
        visitors.add(new RadarEntry(410));
        visitors.add(new RadarEntry(360));
        visitors.add(new RadarEntry(500));
        visitors.add(new RadarEntry(630));

        RadarDataSet radarDataSet = new RadarDataSet(visitors, "Visitors");
        radarDataSet.setColor(Color.RED);
        radarDataSet.setLineWidth(2f);
        radarDataSet.setValueTextColor(Color.RED);
        radarDataSet.setValueTextSize(14f);

        // Vesitors 2
        ArrayList<RadarEntry> visitors2 = new ArrayList<>();
        visitors2.add(new RadarEntry(350));
        visitors2.add(new RadarEntry(620));
        visitors2.add(new RadarEntry(410));
        visitors2.add(new RadarEntry(360));
        visitors2.add(new RadarEntry(710));
        visitors2.add(new RadarEntry(660));

        RadarDataSet radarDataSet2 = new RadarDataSet(visitors2, "Visitors2");
        radarDataSet2.setColor(Color.BLUE);
        radarDataSet2.setLineWidth(2f);
        radarDataSet2.setValueTextColor(Color.BLUE);
        radarDataSet2.setValueTextSize(14f);


        RadarData radarData = new RadarData();
        radarData.addDataSet(radarDataSet);
        radarData.addDataSet(radarDataSet2);


        String[] labels = {"2014","2015","2016","2017","2018","2019"};
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        radarChart.getDescription().setText("Radar Chart Example");
        radarChart.setData(radarData);
    }
}
