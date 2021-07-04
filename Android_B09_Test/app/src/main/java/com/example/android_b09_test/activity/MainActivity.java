package com.example.android_b09_test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.android_b09_test.R;
import com.example.android_b09_test.adapter.StudentAdapter;
import com.example.android_b09_test.constants.Constants;
import com.example.android_b09_test.database.Database;
import com.example.android_b09_test.databinding.ActivityMainBinding;
import com.example.android_b09_test.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private StudentAdapter adapter;
    private List<Student> students;
    private Database stDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        stDb = new Database(this);

        students = stDb.getAll();
        adapter = new StudentAdapter(students,this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.fBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddStudentActivity.class));
            }
        });

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if(TextUtils.isEmpty(charSequence) || charSequence.equals("")){
                    students = stDb.getAll();
                    adapter = new StudentAdapter(students, MainActivity.this);
                    binding.recyclerView.setAdapter(adapter);
                }
                else{

                    // Biến đổi chuỗi viết hoa chữ cái đầu
                    String str = removeAccent(charSequence.toString()).toLowerCase();

                    // Tìm kiếm gần đúng
                    String regex = ".*" + str ; // tìm từ bắt đầu bằng str
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher;
                    ArrayList<Student> newList = new ArrayList<>();
                    for(int i = 0; i < students.size(); i++){
                        matcher = pattern.matcher(removeAccent(students.get(i).getName()).toLowerCase());
                        if(matcher.find()){
                            newList.add(students.get(i));
                        }
                    }
                    StudentAdapter adapterNew = new StudentAdapter(newList, MainActivity.this);
                    adapterNew.notifyDataSetChanged();
                    binding.recyclerView.setAdapter(adapterNew);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    // bỏ dấu câu để match
    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(Constants.SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = Constants.DESTINATION_CHARACTERS[index];
        }
        return ch;
    }

    public static String removeAccent(String str) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }

    public void updateList(List<Student> students){
        this.students = students;
        binding.edtSearch.setText("");
    }

    @Override
    public void onBackPressed() {
        binding.edtSearch.setText("");
    }


}