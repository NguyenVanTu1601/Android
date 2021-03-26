package com.example.android_b04_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android_b04_exercise.adapter.StaffAdapter;
import com.example.android_b04_exercise.model.Staff;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {

    private RadioButton male, female;
    private EditText edtMa, edtName;
    private Button buttonAdd;
    private ListView listView;
    private ArrayList<Staff> staffs;
    public boolean isClick = false;
    private StaffAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();

        adapter = new StaffAdapter(staffs, R.layout.listview_item, this);
        listView.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manv = edtMa.getText().toString();
                String tennv = edtName.getText().toString();
                int sex = 0;
                if (male.isChecked()){
                    sex = 0;
                }else{
                    sex = 1;
                }
                Staff st = new Staff(manv, tennv,sex);
                if (!checkExist(staffs, st)){
                    staffs.add(st);
                }else{
                    Toast.makeText(MainActivity.this, "Exists!", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void init() {
        male        = findViewById(R.id.male);
        female      = findViewById(R.id.female);
        edtMa       = findViewById(R.id.edtMaNV);
        edtName     = findViewById(R.id.edtTenNV);
        buttonAdd   = findViewById(R.id.buttonAdd);
        listView    = findViewById(R.id.listNhanVien);
        staffs = new ArrayList<>();

    }

    public boolean checkExist(ArrayList<Staff> staffs, Staff staff){

        for (Staff st : staffs){
            if (st.getMaNV().equals(staff.getMaNV())){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onLongClick(View view) {
        if (isClick){
            isClick = false;
            Toast.makeText(this, "click = " + isClick, Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }else{
            isClick = true;
            Toast.makeText(this, "click = " + isClick, Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }

        return true;
    }
}
