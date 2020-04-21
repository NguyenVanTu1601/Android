package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edtUser,edtPass;
    CheckBox checkGhiNho;
    Button btnXacNhan;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE); // khởi tạo

        // Lấy dữ liệu
        edtUser.setText(sharedPreferences.getString("user","")); // 2 tham số là tên lưu và giá trị mặc định ban đầu
        edtPass.setText(sharedPreferences.getString("pass",""));
        checkGhiNho.setChecked(sharedPreferences.getBoolean("check",false));

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                if(user.equals("nguyenvantu") && pass.equals("160199")){
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    if(checkGhiNho.isChecked()){
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
                }else{
                    Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void Anhxa(){
        edtUser = (EditText) findViewById(R.id.editText);
        edtPass = (EditText) findViewById(R.id.editText2);
        checkGhiNho = (CheckBox) findViewById(R.id.checkBox);
        btnXacNhan = (Button) findViewById(R.id.button);
    }
}
