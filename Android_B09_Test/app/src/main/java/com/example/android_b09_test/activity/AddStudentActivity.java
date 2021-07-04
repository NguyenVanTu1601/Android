package com.example.android_b09_test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.android_b09_test.database.Database;
import com.example.android_b09_test.databinding.ActivityAddStudentBinding;
import com.example.android_b09_test.model.Student;

public class AddStudentActivity extends AppCompatActivity {

    private ActivityAddStudentBinding binding;
    private Database stDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        stDb = new Database(this);

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.addName.getText().toString();
                String m = binding.addMark.getText().toString();

                boolean gender = true;
                if (binding.addMale.isChecked()){
                    gender = true;
                }else{
                    gender = false;
                }
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(m)){
                    float mark = Float.parseFloat(m);
                    Student student = new Student(name, gender, mark);
                    stDb.addStudent(student);
                    startActivity(new Intent(AddStudentActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(AddStudentActivity.this, "Các trường không để trống !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddStudentActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}