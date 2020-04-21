package com.example.retrofit_okhttp_web;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.retrofit_okhttp_web.Retrofit.APIUtils;
import com.example.retrofit_okhttp_web.Retrofit.DataClient;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * https://square.github.io/retrofit/
 * Giup đọc JSON trở nên đơn giản hơn do ko cần đọc hết về mà vừa đọc vừa phân tách
 * Dùng XAMPP để tạo cơ sở dữ liệu
 * Ap dụng tương tự bài webservice
 * Các bước
 * 1. Tạo file PHP kết nối tới database
 * 2. Tạo file php để
 */

/**
 * Cặp thẻ intent-filter mặc định dùng để xác định xem Activiti nào chạy trước. Nếu muốn chạy trước 1 activity ko phải main
 * thì cắt nó và để vào nơi chứa activity cần chạy trong file manifes
 *
 * Thêm 2 thư viện của retrofit
 * Khởi tạo cấu hình cho retrofit để tái sử dụng :)) . Tạo package retrofit với các class chức năng
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnDangki, btnDangnhap;
    EditText edtTaiKhoan, edtMatKhau;
    String taikhoan = "";
    String matkhau = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();

    }

    private void Anhxa() {
        btnDangki = findViewById(R.id.buttonDangki);
        btnDangnhap = findViewById(R.id.buttonSignIn);
        edtTaiKhoan = findViewById(R.id.editextTaiKhoan);
        edtMatKhau = findViewById(R.id.editextMatKhau);
        btnDangki.setOnClickListener(this);
        btnDangnhap.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDangki:
                startActivity(new Intent(MainActivity.this, DangKi_Activity.class));
                break;
            case R.id.buttonSignIn:
                SignIn();
                break;
        }
    }

    private void SignIn(){
        taikhoan = edtTaiKhoan.getText().toString();
        matkhau = edtMatKhau.getText().toString();

        if(taikhoan.length() > 0 && matkhau.length() > 0){
            DataClient dataClient = APIUtils.getData();
            // gửi dữ liệu lên
            Call<List<Sinhvien>> callback = dataClient.loginData(taikhoan,matkhau);
            // lắng nge trả về
            callback.enqueue(new Callback<List<Sinhvien>>() {
                @Override
                public void onResponse(Call<List<Sinhvien>> call, Response<List<Sinhvien>> response) {
                    // reponse là kq trả về
                    ArrayList<Sinhvien> sinhviens = (ArrayList<Sinhvien>) response.body(); // lấy dữ liệu
                    if(sinhviens.size() > 0){
                        Intent intent = new Intent(MainActivity.this, Information_Activity.class);
                        intent.putExtra("mangsinhvien", sinhviens);
                        // để gửi 1 mảng đối tượng, array thì implement Parcelable trong class sinhvien
                        startActivity(intent);
                    }
                    // Nếu không có tài khoản thì sẽ trả về null và nhảy vào onFailte
                }

                @Override
                public void onFailure(Call<List<Sinhvien>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
