package com.example.android_kt2_bai2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.example.android_kt2_bai2.MainActivity;
import com.example.android_kt2_bai2.R;
import com.example.android_kt2_bai2.database.Database;
import com.example.android_kt2_bai2.databinding.ActivityAddDonateBinding;
import com.example.android_kt2_bai2.model.Donation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddDonateActivity extends AppCompatActivity {

    private ActivityAddDonateBinding binding;
    private Database database;
    private String[] cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDonateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addSpinner();

        database = new Database(this);
        binding.addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddDonateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        binding.addTime.setText(dayOfMonth + " / " + (month + 1) + " / " + year);
                    }
                }, mYear, mMonth, mDay);
                dialog.show();
            }
        });



        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.addName.getText().toString();
                String city = binding.addCity.getSelectedItem().toString();
                String date = binding.addTime.getText().toString();
                int money = Integer.parseInt(binding.addMoney.getText().toString());

                Donation donation = new Donation(name, city, date, money);
                database.addDonation(donation);
                startActivity(new Intent(AddDonateActivity.this, MainActivity.class));
                finish();
            }
        });

        binding.clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddDonateActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void addSpinner() {
       cities = new String[]{"Hà Nội", "Hà Nam", "Thái Bình", "Đà Nẵng", "TP.HCM"};
       ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner, cities );
       adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
       binding.addCity.setAdapter(adapter);
    }
}