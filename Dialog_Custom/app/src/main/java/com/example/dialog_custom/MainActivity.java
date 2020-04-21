package com.example.dialog_custom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txtLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLogin = (TextView) findViewById(R.id.textViewLogin);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_Login();
            }
        });

    }
    private void Dialog_Login(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // bỏ phần khoảng trắng title bên trên dialog
        //dialog.setTitle("Thông báo");
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCanceledOnTouchOutside(false); // kích bên ngoài ko bị mất dialog
        // Ánh xạ
        final EditText edtUser    = (EditText) dialog.findViewById(R.id.editTextUser);
        final EditText edtPass    = (EditText) dialog.findViewById(R.id.editTextPassword);
        Button btnXacnhan   = (Button) dialog.findViewById(R.id.buttonDangnhap);
        Button btnHuy       = (Button) dialog.findViewById(R.id.buttonHuy);
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtUser.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                if(user.equals("tunguyen") && pass.equals("160199")){
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel(); //  tắt dialog
                //dialog.dismiss();
            }
        });
        dialog.show();
    }
}
