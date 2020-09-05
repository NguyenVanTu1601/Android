package com.example.chat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Login_Activity extends AppCompatActivity {

    private FirebaseUser currenUser;
    private Button LoginButton, PhoneLoginButton;
    private EditText UserEmail, UserPassword;
    private TextView needNewAccountLink, ForgetPasswordLink;
    private ImageView ImageLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        Anhxa();
        openRegister();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogin();
            }
        });

        PhoneLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this,PhoneLoginActivity.class));
                finish();
            }
        });

        ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
        
    }

    private void Anhxa() {
        LoginButton = findViewById(R.id.login_button);
        PhoneLoginButton = findViewById(R.id.phone_login_button);
        UserEmail = findViewById(R.id.login_email);
        UserPassword = findViewById(R.id.login_password);
        needNewAccountLink = findViewById(R.id.need_new_account);
        ForgetPasswordLink = findViewById(R.id.forget_password_link);
        ImageLogin = findViewById(R.id.login_image);
        mAuth = FirebaseAuth.getInstance();

        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private void openRegister(){
        needNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation_Bitween_Activity();
            }
        });
    }

    private void SendUserToMainActivity(){
        Intent intent = new Intent(Login_Activity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void Animation_Bitween_Activity(){
        Intent intent = new Intent(Login_Activity.this,RegisterActivity.class);
        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View,String>(ImageLogin,"imageTransition");
        pairs[1] = new Pair<View,String>(UserEmail,"nameTransition");
        pairs[2] = new Pair<View,String>(UserPassword,"descTransition");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login_Activity.this,pairs);
        startActivity(intent,options.toBundle());
    }

    private void UserLogin(){
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(Login_Activity.this, "Vui lòng nhập email...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(Login_Activity.this, "Vui lòng nhập password...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Đăng nhập
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            currenUser = mAuth.getCurrentUser();
                            // Xử lý cho Notification ở mục 19 document
                            String currentUserID = mAuth.getCurrentUser().getUid();
                            String deviceToken = FirebaseInstanceId.getInstance().getToken();
                            UserRef.child(currentUserID).child("device_token").setValue(deviceToken)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        // Chuyển màn hình
                                        SendUserToMainActivity();
                                    }
                                }
                            });

                            // Chuyển màn hình
                            //SendUserToMainActivity();
                        }else{
                            Toast.makeText(Login_Activity.this, "Đăng nhập thất bại...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void forgotPassword(){
        startActivity(new Intent(Login_Activity.this,ResetPassActivity.class));
    }
}
