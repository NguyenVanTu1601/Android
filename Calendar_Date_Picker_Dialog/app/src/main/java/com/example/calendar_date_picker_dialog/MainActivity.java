package com.example.calendar_date_picker_dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText edtDate ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtDate = (EditText) findViewById(R.id.editText);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chonngay();
            }
        });

    }
    private void Chonngay(){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                // i : năm , i1 : tháng, i2 : ngày
                calendar.set(i,i1,i2);
                SimpleDateFormat sim = new SimpleDateFormat("dd/MM/yyyy");
                edtDate.setText(sim.format(calendar.getTime()));
            }
        }, nam,thang,ngay); // truyền 5 tham số
        datePickerDialog.show();
    }
}
