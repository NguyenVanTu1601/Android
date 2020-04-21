package com.example.intent_explicit_truyen_nhan_bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                Bundle bundle = new Bundle();
                int a[] = {1,2,3};
                Sinhvien sv = new Sinhvien("B17DCCN642", "Nguyễn Văn Tú", 3.25f);
                bundle.putString("chuoi", "Hello Tu!");
                bundle.putInt("so", 5);
                bundle.putIntArray("arrayInt", a);
                bundle.putSerializable("Doituong", sv); // sinhvien phai implement Serialable
                intent.putExtra("Dulieu",bundle);
                startActivity(intent);
            }
        });
    }
}
