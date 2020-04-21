package com.example.webservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Add_Sinhvien_Activity extends AppCompatActivity {
    EditText edtHoten, edtNamsinh, edtDiaChi;
    Button btnThem, btnHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__sinhvien_);
        Anhxa();
        final String url = "http://192.168.1.5/android_webservice/insertData.php";
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themSinhVien(url);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void Anhxa(){
        edtHoten = findViewById(R.id.editTextTen);
        edtNamsinh = findViewById(R.id.editTextNamSinh);
        edtDiaChi = findViewById(R.id.editTextDiaChi);
        btnThem = findViewById(R.id.buttonThem);
        btnHuy = findViewById(R.id.buttonHuy);
    }
    private void themSinhVien(String url){
        // Đẩy dữ liệu
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // nếu đọc được thì nó trả kq từ web là success! (chính là câu if else trong php insertData))
                if(response.trim().equals("success!")){
                    Toast.makeText(Add_Sinhvien_Activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Add_Sinhvien_Activity.this,MainActivity.class));
                }else{
                    Toast.makeText(Add_Sinhvien_Activity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_Sinhvien_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){// đẩy dữ liệu
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hotenSV",edtHoten.getText().toString().trim()); // Key là cái trong file php để trong [] khi khai báo
                params.put("namsinhSV", edtNamsinh.getText().toString().trim());
                params.put("diachiSV",edtDiaChi.getText().toString().trim());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
