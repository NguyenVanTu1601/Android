package com.example.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Dùng boundService sẽ chỉ cần ghi đề onBind. Tuy nhiên ngta sẽ thường kết hợp cả 2 loại service vào trong 1 class
 * Đồng thời ghi đè lại phương thức của cả 2 cho phù hợp
 * Tạo hàm getCurrent() để thấy sự tương tác giữa ng dùng và service
 */
public class Bound_Service extends Service {
    //Thao thác : 1.overrider phương thức chính
    // 2. Tạo class BoundExamble hoặc các lớp khác tương tự extent Binder mục đích để tạo ra thằng iBinder bước 3,4
    // 3. Khai báo iBinder
    // 4. Return iBinder trong hàm onBind, iBinder này sẽ truyền sang Main, nhờ đó gọi các hàm
    IBinder iBinder = new BoundExamble();

    @Override
    public void onCreate() {
        Toast.makeText(this, "BoundService được khởi tạo", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "BoundService chạy", Toast.LENGTH_SHORT).show();
        return iBinder;
        // ibider này sẽ truyền sang connection của MainActivity
        // Nhờ đó có thể gọi các hàm trong boundSerVice
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "Server đang hủy", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Hủy hoàn tất", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    public class BoundExamble extends Binder{
        public Bound_Service getService(){ // phương thức này để trả về thằng service để gọi các phương thức trong service
            return Bound_Service.this;
        }
    }

    // Phương thức chính cần thực hiện
    public String getCurrent(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy");
        return simpleDateFormat.format(new Date());
    }
}
