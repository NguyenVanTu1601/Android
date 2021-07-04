package com.example.android_b09_test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android_b09_test.R;
import com.example.android_b09_test.database.Database;
import com.example.android_b09_test.databinding.ActivityEditStudentBinding;
import com.example.android_b09_test.model.Student;

public class EditStudentActivity extends AppCompatActivity {

    private ActivityEditStudentBinding binding;
    private Database stDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stDb = new Database(this);
        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("student");

        binding.editId.setText("ID sinh viên : " + student.getId());
        binding.editName.setText(student.getName());
        binding.editMark.setText(String.valueOf(student.getMark()));
        if (student.isGender()){
            binding.editMale.setChecked(true);
        }else{
            binding.editFemale.setChecked(true);
        }

        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editName.getText().toString();
                int id = student.getId();
                float mark = Float.parseFloat(binding.editMark.getText().toString());
                boolean gender = true;
                if (!binding.editMale.isChecked()){
                    gender = false;
                }

                Student st = new Student(id, name, gender, mark);
                if (stDb.updateStudent(st) > 0){
                    Toast.makeText(EditStudentActivity.this, "Sửa thông tin thành công!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditStudentActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(EditStudentActivity.this, MainActivity.class));
                finish();
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditStudentActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}