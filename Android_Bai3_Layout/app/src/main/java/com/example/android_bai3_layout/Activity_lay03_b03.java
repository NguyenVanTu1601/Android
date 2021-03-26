package com.example.android_bai3_layout;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_bai3_layout.databinding.Layout03Bai03Binding;

public class Activity_lay03_b03 extends AppCompatActivity {

    Layout03Bai03Binding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Layout03Bai03Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        init();

        binding.btnSubmit03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                String platform = "Platform : ";
                if (binding.boxIphone.isChecked()){
                    platform += "Iphone, ";
                }
                if (binding.boxAndroid.isChecked()){
                    platform += "Android, ";
                }
                if (binding.boxWin.isChecked()){
                    platform += "Windows phone, ";
                }

                platform = platform.substring(0, platform.length() - 2);
                text += platform + "\n";

                String spinCountry = binding.spinnerCountry.getSelectedItem().toString();
                String spinUniver = binding.spinnerUniver.getSelectedItem().toString();
                text += "Country : " + spinCountry + "\n" + "Univer : " + spinUniver + "\n";

                if (binding.radioGroup.getCheckedRadioButtonId() == R.id.male){
                    text += "Sex = Male \n";
                }else{
                    text += "Sex = Female\n";
                }

                text += "Rating : " + binding.rating.getRating() + "\n";

                binding.result.setText(text);

            }
        });
    }

    private void init() {

        String[] country = {"Việt Nam", "Lào", "Campuchia","Trung Quốc"};
        String[] univer = {"PTIT", "HUST", "FTU"};
        ArrayAdapter adapterCountry = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item , country);
        adapterCountry.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.spinnerCountry.setAdapter(adapterCountry);

        ArrayAdapter adapterUniver = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item , univer);
        adapterCountry.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.spinnerUniver.setAdapter(adapterUniver);
    }
}
