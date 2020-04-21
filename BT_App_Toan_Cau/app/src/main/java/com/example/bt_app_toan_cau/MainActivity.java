package com.example.bt_app_toan_cau;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtthongtin;
    Button xacnhan;
    EditText edtHoten, edtSDT , edtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anh_xa();
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edtHoten.getText().toString();
                String email = edtEmail.getText().toString();
                String sdt   = edtSDT.getText().toString();

                String xin_chao = getResources().getString(R.string.txt_Xinchao);

                txtthongtin.setText(xin_chao + ": " + hoten + "\n" + email + "\n" + sdt);
            }
        });
    }
    private void Anh_xa(){
        txtthongtin = (TextView)findViewById(R.id.thong_tin);
        xacnhan     = (Button) findViewById(R.id.xac_nhan);
        edtHoten    = (EditText) findViewById(R.id.hoten);
        edtEmail    = (EditText) findViewById(R.id.email);
        edtSDT      = (EditText) findViewById(R.id.SĐT);
    }
}
