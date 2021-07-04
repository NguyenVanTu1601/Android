package com.example.android_b09_sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.android_b09_sqlite.adapter.RecyclerViewAdapter;
import com.example.android_b09_sqlite.database.SQLStudentOpenHelper;
import com.example.android_b09_sqlite.databinding.ActivityMainBinding;
import com.example.android_b09_sqlite.model.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private SQLiteDatabase database;
    private SQLStudentOpenHelper sqlStudentOpenHelper;
    private RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new RecyclerViewAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        sqlStudentOpenHelper = new SQLStudentOpenHelper(this);

        binding.btnAdd.setOnClickListener(view -> {
            String name = binding.stdName.getText().toString();
            boolean gender;
            if (binding.rbMale.isChecked()){
                gender = true;
            }else{
                gender = false;
            }
            double mark = Double.parseDouble(binding.stdMark.getText().toString());
            Student st = new Student(name, gender, mark);
            sqlStudentOpenHelper.addStudent(st);
        });

        binding.btnGetAll.setOnClickListener(view -> {
            List<Student> list = sqlStudentOpenHelper.getAll();
            adapter.setStudents(list);
            adapter.notifyDataSetChanged();
            binding.recyclerView.setAdapter(adapter);
        });
    }
}