package com.example.android_b08_intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.android_b08_intent.databinding.ActivityLoginBinding;

/**
 * LoginActivity chứa 2 button :
 * + Button Register dùng để tạo 1 intent đưa vào startActivityForResult để nhận kết quả từ RegisterActivity
 * + Button Login để đăng nhập
 *
 * RegisterActivity chứa 2 button
 * + Button Register dùng để tạo 1 intent kết quả và gán kết quả vào intent thông qua setResult()
 *   để trả về cho startActivityForResult của Login.
 *   Sau setResult phải có finish để kết thúc RegisterActivity hiện tại
 */
public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // truyền một intent sang register thông qua startForResult để đón nhận kq username, pass từ regisster gửi về
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(registerIntent,Constants.REQUEST_CODE);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.edtUsername.getText().toString();
                String password = binding.edtPassword.getText().toString();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                    Intent intent = new Intent(LoginActivity.this, Home.class);
                    intent.putExtra(Constants.USERNAME, username);
                    intent.putExtra(Constants.PASSWORD, password);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập, mật khẩu trống!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    // hàm đón nhận kq và show lên giao diện để login
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null){
            binding.edtUsername.setText(data.getStringExtra(Constants.USERNAME));
            binding.edtPassword.setText(data.getStringExtra(Constants.PASSWORD));
        }else{
            Toast.makeText(this, "Bạn đã hủy đăng kí tài khoản", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}