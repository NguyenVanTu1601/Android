package com.example.broadcast_receiver;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) { // intent có thẻ lấy tất cả thông tin mà hệ thống gửi
        String s = intent.getStringExtra("extra");
        if(s != null){
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(context, "Network change!", Toast.LENGTH_SHORT).show();
    }
}
