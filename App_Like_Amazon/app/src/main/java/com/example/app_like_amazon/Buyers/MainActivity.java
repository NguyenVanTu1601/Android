package com.example.app_like_amazon.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_like_amazon.Model.Users;
import com.example.app_like_amazon.Prevalent.Prevalent;
import com.example.app_like_amazon.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button joinNowButton, loginButton;
    private DatabaseReference RootRef;
    private ProgressDialog loadingBar;
    private TextView txtToSellerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        ButtonAction();
        LoginAlway();

    }


    private void initialization(){
        joinNowButton   = findViewById(R.id.main_join_now_btn);
        loginButton     = findViewById(R.id.main_login_btn);
        RootRef = FirebaseDatabase.getInstance().getReference();
        loadingBar = new ProgressDialog(this);
        txtToSellerLogin = findViewById(R.id.seller_login);
        //Gọi Paper để đăng nhập luôn
        Paper.init(this);
    }

    private void ButtonAction(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void LoginAlway(){
        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if(UserPhoneKey != "" && UserPasswordKey != ""){
            if(!TextUtils.isEmpty(UserPasswordKey) && !TextUtils.isEmpty(UserPhoneKey)){
                    AllowSuccess(UserPhoneKey,UserPasswordKey);
            }
        }
    }

    private void AllowSuccess(final String userPhoneKey, final String userPasswordKey) {
        loadingBar.setTitle("Login account");
        loadingBar.setMessage("Please wait ...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(userPhoneKey).exists()){
                    Users userData = dataSnapshot.child("Users").child(userPhoneKey).getValue(Users.class);

                    if(userData.getPhone().equals(userPhoneKey) && userData.getPassword().equals(userPasswordKey)){

                        loadingBar.dismiss();
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        Prevalent.currentOnlineUser = userData;
                        startActivity(intent);

                    }else{

                        loadingBar.dismiss();
                        Toast.makeText(MainActivity.this, "Số điện thoại hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Đăng nhập thất bại...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
