package com.example.checkinternetconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private Button btnCheck;
    private LinearLayout layoutConnected, layoutDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCheck = findViewById(R.id.btnCheck);
        layoutConnected = findViewById(R.id.layoutConnect);
        layoutDisconnect = findViewById(R.id.layoutDisconnect);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()){
                    layoutConnected.setVisibility(View.VISIBLE);
                    layoutDisconnect.setVisibility(View.GONE);
                }else{
                    layoutDisconnect.setVisibility(View.VISIBLE);
                    layoutConnected.setVisibility(View.GONE);
                }
            }
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}