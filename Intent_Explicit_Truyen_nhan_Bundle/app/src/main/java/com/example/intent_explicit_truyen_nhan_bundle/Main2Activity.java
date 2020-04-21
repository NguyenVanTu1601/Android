package com.example.intent_explicit_truyen_nhan_bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txt = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Dulieu");
        // Neeu sai name thif bundle se bij null
        if(bundle != null){
            String s = bundle.getString("chuoi");
            txt.setText(s);
        }

    }
}
