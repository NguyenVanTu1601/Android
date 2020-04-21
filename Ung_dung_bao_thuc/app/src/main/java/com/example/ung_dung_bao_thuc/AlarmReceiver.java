package com.example.ung_dung_bao_thuc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    // Dùng để nhận các intent từ hệ thống hoặc trao đổi dữ liệu giữa các ứng dụng
    // Để sử dụng thì cần khai bào trong manifes dưới thẻ activity
    // Class này có công dụng vd khi mình đặt thời gian là 10h thì tới thời gian này nó sẽ chạy vào đây
    // Sử dụng cái penđingIntent bên Main
    @Override
    public void onReceive(Context context, Intent intent) {
        String trang_thai = intent.getStringExtra("extra");
        Intent myIntent = new Intent(context, Music.class);
        myIntent.putExtra("extra",trang_thai); // truyền trạng thái nhận từ main qua music
        context.startService(myIntent); // Khi gọi tới thằng này thì lập tức gọi tới server và yêu cầu thực hiện

    }
}
