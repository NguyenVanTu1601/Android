package com.example.fragment_bt_xu_ly_giao_dien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Student_Infomation_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__infomation_);
        Intent intent = getIntent();
        SinhVien sv = (SinhVien) intent.getSerializableExtra("info");

        Fragment_student_info fragment_student_info = (Fragment_student_info) getSupportFragmentManager().findFragmentById(R.id.fragmentChitietSV);
        fragment_student_info.setInfo(sv);
    }
}
