package com.example.firebase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Vào firebase -> authentication->sign method->Email/Password lấy thư viện
 * Sau đó bật enable Password . Đọc phần document phần authentication->android-> passwordAuthentication
 * Test trên máy thật do máy ảo chưa có dịch vụ của google
 *
 */
public class MainActivity extends AppCompatActivity {
    EditText edtEmail, edtPass;
    Button btnDangKi, btnDangNhap;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    private void Anhxa() {
        edtEmail  = findViewById(R.id.editTextEmail);
        edtPass   = findViewById(R.id.editTextPassword);
        btnDangKi = findViewById(R.id.buttonDangKy);
        btnDangNhap = findViewById(R.id.buttonDangNhap);
        mAuth     = FirebaseAuth.getInstance();
    }

    private void createAccount(){
        String email = edtEmail.getText().toString().trim();
        String password = edtPass.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful() == true){
                            Toast.makeText(MainActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Lỗi đăng kí", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SignIn(){
        String email = edtEmail.getText().toString().trim();
        String password = edtPass.getText().toString();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful() == true){
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}


