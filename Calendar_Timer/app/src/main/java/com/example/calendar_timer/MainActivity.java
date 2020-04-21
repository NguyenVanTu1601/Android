package com.example.calendar_timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txtTime = (TextView) findViewById(R.id.textViewTime);
        Calendar calendar = Calendar.getInstance(); // gói java.util
        txtTime.append(calendar.getTime() + "\n"); // trả về ngày tháng năm giờ phút giây, thứ, múi giờ
        txtTime.append(calendar.get(Calendar.DATE) + "\n");
        txtTime.append(calendar.get(Calendar.MONTH) + "\n"); // tháng tính từ 0
        txtTime.append(calendar.get(Calendar.YEAR) + "\n");
        SimpleDateFormat dinhdang = new SimpleDateFormat("dd/MM/yyyy");
        txtTime.append(dinhdang.format(calendar.getTime()) + "\n");
        txtTime.append(calendar.get(Calendar.HOUR_OF_DAY) + "\n"); // định dạng 24h
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
        txtTime.append(simpleDateFormat.format(calendar.getTime()) + "\n");
    }
}
