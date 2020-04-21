package com.example.boundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * BoundService cho phép tương tác với cline.
 * VD đơn giản nhất là play nhạc khi chạy nền cần đổi bài khác thì có thể tác động
 * Nhớ khai báo trong manifes
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnStart, btnStop;
    private boolean isboundservice = false;
    Bound_Service bound_service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.startBoundService);
        btnStop = findViewById(R.id.stopBoundService);
        btnStop.setOnClickListener(this);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, Bound_Service.class);
        switch (v.getId()){
            case R.id.startBoundService:
                bindService(intent, connection, BIND_AUTO_CREATE); // 3 tham số là intent, connection và cơ hiệu
                // Cờ này là ktra service đã tạo chưa...
                break;
            case R.id.stopBoundService:
                // Nếu ấn button này 2 lần sẽ xảy ra lỗi do service unregister ở lần 2
                // do đo cân biến boundservice để ktra
                if(isboundservice == true){
                    isboundservice = false;
                    unbindService(connection);
                }

                break;
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // phương thức này đưcọ gọi khi service được kết nối sau khi gọi bindService để khởi tạo
            Bound_Service.BoundExamble ibider = (Bound_Service.BoundExamble) service;
            bound_service = ibider.getService();// getService là hàm bên Bound_Service

            Toast.makeText(MainActivity.this, bound_service.getCurrent()+" Connection", Toast.LENGTH_SHORT).show();
            isboundservice = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // Phương thức này chỉ được gọi khi bị ngắt kết nối đột ngột vd bị lỗi hay tắt bỏ
            // khi gọi unbindService thì phương thức này ko được gọi
            Toast.makeText(MainActivity.this, "Service Disconnect!", Toast.LENGTH_SHORT).show();
            isboundservice = false;
        }
    };
}
