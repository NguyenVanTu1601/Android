package com.example.messenger_socketio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Signup_Activity extends AppCompatActivity {
    EditText edtHoTen, edtTaikhoan, edtMatkhau, edtSDT;
    ImageView imgAvata;
    Button btnHuySigup, btnXacNhanSignup;
    ImageView imgFoleder, imgCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
        Anhxa();
        HuySignUp();
        XacNhanSignUp();
        OpenFolder();
        OpenCamera();
    }

    private void XacNhanSignUp(){

    }

    private void HuySignUp() {
        btnHuySigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup_Activity.this, SignIn_Activity.class));
            }
        });
    }

    private void OpenFolder(){

    }

    private void OpenCamera(){

    }
    private void Anhxa() {
        edtHoTen         = findViewById(R.id.HotenSignup);
        edtMatkhau       = findViewById(R.id.matkhausignup);
        edtTaikhoan      = findViewById(R.id.taikhoansignup);
        edtSDT           = findViewById(R.id.sodienthoaisignup);
        imgAvata         = findViewById(R.id.avatasignup);
        btnHuySigup      = findViewById(R.id.buttonHuySignUp);
        btnXacNhanSignup = findViewById(R.id.buttonXacNhanSignUp);
        imgCamera        = findViewById(R.id.Camera);
        imgFoleder       = findViewById(R.id.folfer_Image);
    }
}
