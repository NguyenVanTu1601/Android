package com.example.checkinternetstate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCheckInternet = findViewById(R.id.btnCheckInternet);
        btnCheckInternet.setOnClickListener(view -> {
            if (isNetworkAvailable(MainActivity.this)){
                Toast.makeText(MainActivity.this, "Internet sẵn sàng!!!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this, "Not available internet!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        } else {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) {
                return false;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Network network = connectivityManager.getActiveNetwork();
                if (network == null) {
                    return false;
                } else {
                    NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                    return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
                }
            } else {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
        }
    }
}