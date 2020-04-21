package com.example.messenger_socketio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn_Activity extends AppCompatActivity {
    Button btnLogin;
    EditText edtUser, edtPassword;
    TextView txtSignUp;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    private boolean isCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);
        Anhxa();
        LogIn();
        SignUp();
    }

    private void Anhxa() {
        btnLogin     = findViewById(R.id.buttonLogin);
        edtUser      = findViewById(R.id.editTextUserLogIn);
        edtPassword  = findViewById(R.id.editTextPasswordLogin);
        txtSignUp    = findViewById(R.id.TextViewSignup);
        checkBox     = findViewById(R.id.checkGhiNho);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE); // khởi tạo
    }

    private void LogIn() {
        // Lấy dữ liệu từ sharePreferences
        String s = sharedPreferences.getString("user","");
        if(s.equals("") == false){
            edtUser.setText(sharedPreferences.getString("user",""));
            edtPassword.setText(sharedPreferences.getString("pass",""));
            checkBox.setChecked(sharedPreferences.getBoolean("check",false));
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPassword.getText().toString();
                if(user.equals("banggia") && pass.equals("160199")){
                    isCorrect = true;
                }
                if(isCorrect){
                    // Lưu thông tin đăng nhập
                    SaveSignin(user,pass);

                    // Gửi dữ liệu qua cho bên MainActivty
                    Intent intent = new Intent(SignIn_Activity.this,MainActivity.class);
                    intent.putExtra("User-connection","banggia");
                    startActivity(intent);

                }
            }
        });
    }

    private void SignUp(){
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn_Activity.this,Signup_Activity.class));
            }
        });
    }

    private void SaveSignin(String user, String pass){
        if(checkBox.isChecked()){
            // Nếu có check thì lưu thông tin lại
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user",user);
            editor.putString("pass",pass);
            editor.putBoolean("check",true);
            editor.commit();
        }
        else{
            // Nếu không check thì phải xóa thông tin cũ đã lưu đi để lần đăng nhập tiếp ko lấy giá trị nữa
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("user");
            editor.remove("pass");
            editor.remove("check");
            editor.commit();
        }
    }
}
