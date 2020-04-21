package com.example.messenger_socketio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditInformation_Activity extends AppCompatActivity {
    ImageView imgAvata;
    Button btnHuy, btnXacNhan;
    EditText edtHoten, edtSodienthoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information_);
        Anhxa();
    }

    private void Anhxa() {
        imgAvata            = findViewById(R.id.SuaImageUser);
        btnHuy              = findViewById(R.id.buttonHuySua);
        btnXacNhan          = findViewById(R.id.buttonXacNhanSua);
        edtHoten            = findViewById(R.id.SuaHoten);
        edtSodienthoai      = findViewById(R.id.SuaSodienthoai);
    }
}
