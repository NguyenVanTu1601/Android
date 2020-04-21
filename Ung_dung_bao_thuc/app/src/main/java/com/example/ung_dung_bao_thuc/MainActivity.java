package com.example.ung_dung_bao_thuc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.TimeZone;

/**
 *  https://developer.android.com/guide/components/intents-filters#PendingIntent
 *  Xem thêm về pending Intent
 */
public class MainActivity extends AppCompatActivity {
    TimePicker timePicker;
    Button btnDatGio, btnTatbaothuc;
    TextView txtTime;
    Calendar calendar;// lấy thời gian hiện tại

    // B2 sau khi tạo alamRe... khai báo alamMânger , Pending., Intent truyền dữ liệu...
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    // Bước 3 tạo class music chứa service chạy ngầm nhớ khai báo trong manifes và phát nhạc

    // Bước 4 . Dừng phát nhạc , truyền đi 1 intent on/off
    // Truyền từ main -> alarmReceiver -> service music. Khi ấn tắt truyền qua sendBoardcast
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bước 1 : Anh xạ, lấy thời gian từ timePicker
        timePicker = findViewById(R.id.timePicker);
        btnDatGio = findViewById(R.id.buttonDatGio);
        btnTatbaothuc = findViewById(R.id.buttonTatbaothuc);
        txtTime = findViewById(R.id.TextViewTime);
        calendar = calendar.getInstance();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE); // Cho phép truy cập hệ thống báo động của máy, báo động việc báo thức
        final Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

        btnDatGio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());

                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                if(phut < 10){
                    txtTime.setText("Báo thức lúc : " + gio + ":0" + phut);
                }else {
                    txtTime.setText("Báo thức lúc : " + gio + ":" + phut);
                }
                intent.putExtra("extra","on");
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0 , intent,PendingIntent.FLAG_UPDATE_CURRENT );
                // Pending này tồn tại trong suốt ứng dụng ngay cả khi tắt ứng dụng!! ó vẻ có thể sai

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        // Bước 2: Khởi tạo receiver, Alamanager, PendingItent (class alarrmRêciver)


        // Cuối cùng là tắt báo thức
        btnTatbaothuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTime.setText("Tắt báo thức");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","off");
                sendBroadcast(intent);
            }
        });
    }
}
