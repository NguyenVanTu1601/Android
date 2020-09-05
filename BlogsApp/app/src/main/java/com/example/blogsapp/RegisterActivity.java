package com.example.blogsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private TextView textReturnLogin;
    private EditText edtEmail, edtPassword;
    private Button btnRegister;

    private FirebaseAuth mAuth;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialization();

        textReturnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Email và password không được để trống", Toast.LENGTH_SHORT).show();
                    edtEmail.requestFocus();
                    return;
                }else{
                    loadingBar.setTitle("Đang tạo tài khoản...");
                    loadingBar.setMessage("Chờ trong giây lát..");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();

                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        loadingBar.dismiss();
                                        startActivity(new Intent(RegisterActivity.this,AccountSettingActivity.class));
                                        finish();
                                    }else{
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this,"Tạo tài khoản không thành công!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }

    private void initialization() {
        textReturnLogin = findViewById(R.id.edt);
        btnRegister = findViewById(R.id.register_button);
        edtEmail = findViewById(R.id.register_Email);
        edtPassword = findViewById(R.id.register_Password);

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);
    }

}