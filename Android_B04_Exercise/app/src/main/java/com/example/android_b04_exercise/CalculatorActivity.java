package com.example.android_b04_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.android_b04_exercise.databinding.ActivityCalculatorBinding;

public class CalculatorActivity extends AppCompatActivity {

    ActivityCalculatorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a1 = Integer.parseInt(binding.editTextTextPersonName.getText().toString());
                int a2 = Integer.parseInt(binding.editTextTextPersonName2.getText().toString());

                binding.textView.setText((a1 + a2) + "");
            }
        });
    }
}