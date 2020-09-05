package com.example.chat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class RegisterActivity extends AppCompatActivity {
    private Button RegisterButton;
    private EditText UserEmail, UserPassword;
    private TextView alreadyHaveAccount;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference RootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitializaFields();
        openLogin();
        Register();
    }

    // Quay lại màn hình login
    private void openLogin() {
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, Login_Activity.class));
            }
        });
    }

    // Ánh xạ các thành phần
    private void InitializaFields() {
        RegisterButton = findViewById(R.id.register_button);
        UserEmail = findViewById(R.id.register_email);
        UserPassword = findViewById(R.id.register_password);
        alreadyHaveAccount = findViewById(R.id.Already_have_account);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        RootRef = FirebaseDatabase.getInstance().getReference();
    }

    // Đăng kí tài khoản
    private void Register(){
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Lấy email, pass từ text
                String email = UserEmail.getText().toString();
                String password = UserPassword.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập email...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập password...", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Bật progressbar Dialog
                loadingBar.setTitle("Đang tạo tài khoản...");
                loadingBar.setMessage("Chờ trong giây lát..");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();

                // Đăng kí và kiểm tra thành công hay thất bại
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Nếu đăng kí thành công
                                if(task.isSuccessful()){

                                    // Thông báo cho ng dùng
                                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    // Tạo nhánh database có tên trùng ID user
                                    String currenUserId = mAuth.getCurrentUser().getUid();
                                    RootRef.child("Users").child(currenUserId).setValue(""); // tạo nhánh rỗng do chưa có thông tin ng dùng

                                    // Thêm 1 nhánh values nhỏ chứa token .. đoạn này dùng trong mục 19 cơ
                                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                    RootRef.child("Users").child(currenUserId).child("device_token").setValue(deviceToken);

                                    // Quay về màn hình Main
                                    SendUserToMainActivity();

                                }else{
                                    Toast.makeText(RegisterActivity.this, "Đăng kí thất bại...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }
                            }

                        });
            }
        });
    }

    // Chuyển sang Activity khi đã đăng kí thành công
    private void SendUserToMainActivity(){
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
