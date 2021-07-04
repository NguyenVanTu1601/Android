package com.example.android_b08_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android_b08_intent.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        // truyền trả kq về cho activityForResult bên Login
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Constants.USERNAME, binding.edtUsername.getText().toString());
                intent.putExtra(Constants.PASSWORD, binding.edtPassword.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}