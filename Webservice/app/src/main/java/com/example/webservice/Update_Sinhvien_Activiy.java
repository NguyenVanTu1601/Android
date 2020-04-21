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

public class Update_Sinhvien_Activiy extends AppCompatActivity {
    EditText edtHoten, edtNamsinh, edtDiaCHI;
    Button btnSua, btnHuy;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__sinhvien__activiy);
        Intent intent = getIntent();
        SinhVien sinhVien = (SinhVien) intent.getSerializableExtra("dataSinhvien");
        Anhxa();
        edtHoten.setText(sinhVien.getHoten());
        edtDiaCHI.setText(sinhVien.getDiachi());
        edtNamsinh.setText(sinhVien.getNamsinh() + "");
        id = sinhVien.getId();

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdataSinhvien("http://192.168.1.5/android_webservice/updateData.php");
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
        edtHoten = findViewById(R.id.editTextSuaHoten);
        edtNamsinh = findViewById(R.id.editTextSuaNamSinh);
        edtDiaCHI = findViewById(R.id.editTextSuaDiaChi);
        btnSua = findViewById(R.id.buttonCapNhat);
        btnHuy = findViewById(R.id.buttonHuyEdit);
    }

    private void UpdataSinhvien(String url){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success!")){
                    Toast.makeText(Update_Sinhvien_Activiy.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Update_Sinhvien_Activiy.this,MainActivity.class));
                }else{
                    Toast.makeText(Update_Sinhvien_Activiy.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Sinhvien_Activiy.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idSV",String.valueOf(id));
                params.put("hotenSV", edtHoten.getText().toString());
                params.put("diachiSV", edtDiaCHI.getText().toString());
                params.put("namsinhSV", edtNamsinh.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
