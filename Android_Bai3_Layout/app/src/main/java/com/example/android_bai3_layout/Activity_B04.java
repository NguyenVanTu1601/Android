package com.example.android_bai3_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.android_bai3_layout.databinding.ActivityB04Binding;
import com.example.android_bai3_layout.databinding.Layout03Bai04Binding;

import java.util.Calendar;

public class Activity_B04 extends AppCompatActivity implements View.OnClickListener{

    Layout03Bai04Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Layout03Bai04Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnDate.setOnClickListener(this);
        binding.btnTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnDate){
            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    // trong ondata set là thông tin ng dùng chọn
                    binding.edtDate.setText(dayOfMonth + " - " + (month + 1) + " - " + year);
                }
            }, mYear, mMonth, mDay); // mYear... là mặc định đầu vào

            dialog.show();
        }
        if (v == binding.btnTime){
            Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    binding.edtTime.setText(hourOfDay + " - " + minute);
                }
            }, mHour,mMinute,false); // giờ,phút, chế độ hiển thị 12/24h

            dialog.show();
        }
    }
}