package com.example.retrofit_okhttp_web;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit_okhttp_web.Retrofit.APIUtils;
import com.example.retrofit_okhttp_web.Retrofit.DataClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Information_Activity extends AppCompatActivity {
    Button btnXoa;
    ImageView imgHinh;
    TextView txtTk, txtPass;
    ArrayList<Sinhvien> sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_);
        Intent in = getIntent();
        sv = in.getParcelableArrayListExtra("mangsinhvien");

        Anhxa();
        txtTk.setText("Tài khoản : " + sv.get(0).getTaikhoan());
        txtPass.setText("Mật khẩu : " + sv.get(0).getMatkhau());
        Picasso.with(this).load(sv.get(0).getHinhanh()).into(imgHinh);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Hinh = sv.get(0).getHinhanh();
                String tenHinh = Hinh.substring(Hinh.lastIndexOf("/"));

                DataClient dataClient = APIUtils.getData();
                Call<String> callBack = dataClient.deleteData(sv.get(0).getId(),tenHinh);
                Toast.makeText(Information_Activity.this, sv.get(0).getId(), Toast.LENGTH_SHORT).show();
                callBack.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String ketqua = response.body();
                        if(ketqua.equals("success")){
                            Toast.makeText(Information_Activity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Information_Activity.this, "Không xóa được", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(Information_Activity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void Anhxa() {
        btnXoa = findViewById(R.id.button_xoa);
        txtPass = findViewById(R.id.infor_matkhau);
        imgHinh = findViewById(R.id.infor_image);
        txtTk = findViewById(R.id.infor_taikhoan);
    }
}
