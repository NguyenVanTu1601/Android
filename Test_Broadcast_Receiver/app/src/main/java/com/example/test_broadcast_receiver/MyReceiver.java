package com.example.test_broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String result = intent.getStringExtra("extra");
        if(result.equals("true")){
            Toast.makeText(context, "Broadcast Receiver !", Toast.LENGTH_SHORT).show();
        }
    }
}
