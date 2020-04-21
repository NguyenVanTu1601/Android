package com.example.bt_demtime_calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText edtNgay1,edtNgay2;
    Button btnClick;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Calendar calendarOne, calendarTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        edtNgay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay1();
            }
        });
        edtNgay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay2();
            }
        });
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int kcNgay = (int) ((calendarTwo.getTimeInMillis() - calendarOne.getTimeInMillis()))/(1000*60*60*24);
                if(kcNgay < 0) Toast.makeText(MainActivity.this, "Vui lòng chọn ngày 2 sau ngày 1", Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this, kcNgay + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Anhxa(){
        btnClick = (Button) findViewById(R.id.buttonClick);
        edtNgay1 = (EditText) findViewById(R.id.editTextNgay1);
        edtNgay2 = (EditText) findViewById(R.id.editTextNgay2);
    }
    private void ChonNgay1(){
        calendarOne = Calendar.getInstance();
        int nam = calendarOne.get(Calendar.YEAR);
        int thang = calendarOne.get(Calendar.MONTH);
        int ngay = calendarOne.get(Calendar.DATE);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendarOne.set(i,i1,i2);
                edtNgay1.setText(simpleDateFormat.format(calendarOne.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    private void ChonNgay2(){
        calendarTwo = Calendar.getInstance();
        int nam = calendarTwo.get(Calendar.YEAR);
        int thang = calendarTwo.get(Calendar.MONTH);
        int ngay = calendarTwo.get(Calendar.DATE);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendarTwo.set(i,i1,i2);
                edtNgay2.setText(simpleDateFormat.format(calendarTwo.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }
}
