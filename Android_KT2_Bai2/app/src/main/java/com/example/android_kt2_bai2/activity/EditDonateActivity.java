package com.example.android_kt2_bai2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.android_kt2_bai2.MainActivity;
import com.example.android_kt2_bai2.R;
import com.example.android_kt2_bai2.database.Database;
import com.example.android_kt2_bai2.databinding.ActivityEditDonateBinding;
import com.example.android_kt2_bai2.model.Donation;

import java.util.Calendar;

public class EditDonateActivity extends AppCompatActivity {

    private ActivityEditDonateBinding binding;
    private Database database;
    private String[] cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditDonateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addSpinner();
        database = new Database(this);
        Intent intent = getIntent();
        Donation donation = (Donation) intent.getSerializableExtra("donation");
        if (donation != null){
            binding.edtName.setText(donation.getName());
            binding.edtId.setText(donation.getId() +"");
            binding.edtMoney.setText(donation.getMoney() + "");
            binding.edtTime.setText(donation.getDate());
            int position = 0;
            for(int i = 0; i < cities.length; i++){
                if (cities[i].equals(donation.getCity())){
                    position = i;
                    break;
                }
            }
            binding.edtCity.setSelection(position);
        }

        binding.edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditDonateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        binding.edtTime.setText(dayOfMonth + " / " + (month + 1) + " / " + year);
                    }
                }, mYear, mMonth, mDay);
                dialog.show();
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (database.deleteDonation(donation.getId()) > 0){
                    Toast.makeText(EditDonateActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditDonateActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.edtName.getText().toString();
                String city = binding.edtCity.getSelectedItem().toString();
                String date = binding.edtTime.getText().toString();
                int money = Integer.parseInt(binding.edtMoney.getText().toString());
                int id = Integer.parseInt(binding.edtId.getText().toString());
                Donation donation = new Donation(id,name, city, date, money);
                if (database.updateDonation(donation) > 0){
                    Toast.makeText(EditDonateActivity.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditDonateActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void addSpinner() {
        cities = new String[]{"Hà Nội", "Hà Nam", "Thái Bình", "Đà Nẵng", "TP.HCM"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner, cities );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.edtCity.setAdapter(adapter);
    }
}