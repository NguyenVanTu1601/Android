package com.example.broadcast_receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *Là 1 trong 4 phần kiến thức cơ bản : Activity,  Broadcast Receiver, Service, Content Provider
 *
 * 1. Mục đích sử dụng :
 *  - Dùng để phát đi các sự kiện, trạng thái của hệ thống thông qua intent
 *  - Có thể hoạt động khi tắt ứng dụng đi
 *  VD như ứng dụng báo thức, messenger. Với mess khi tắt mạng thì nếu bật mạng lại, receiver sẽ liên tục kiểm tra
 *  nếu có kết nối mạng sẽ lập tức phản hồi và nhận tin nhắn về
 *  Hoặc khi nhận được mã OTP receiver có thể lắng nge sự kiện có nhận được tin nhắn từ số điện thoại nào đó ko.
 *  Nếu nhận được sẽ tiến hành tự nhập OTP và úng dụng như ViettelPay
 *
 *  - Nó thường được sử dụng với service
 *
 *  2. Khi nào sử dụng
 *  Khi cần lắng nghe sự kiện thay đổi mạng, blutooth, pinyeu, lắng nge tin nhắn, cuộc gọi
 *
 *  3 Cách sử dụng : Có 2 cách
 *  + Đăng kí trong android manifes
 *  + Đăng kí sử lý trong code java
 *
 * 4. Mục tiêu project : Lắng nge sự kiện bật tắt wifi và hiện thông báo turnOff/ turnOn netwwork
 */
public class MainActivity extends AppCompatActivity {
    // Cách 1 : Khai báo trong androidManifes một thẻ receiver với name là tên class extends Receiver(BroadcastRêciver)
             // Đồng thời khai báo 2 acction thay đổi mạng(3g/wifi) và phát ra trạng thái wifi thay đổi.
                   //android.net.conn.CONNECTIVITY_CHANGE

             // Class Broad... trong hàm onReceiver chỉ Toast lên câu thông báo

             // Tuy nhiên project này chỉ cần 1 action thay đổi mạng
             // Ngoài ra có quyền userPemiss : ACCESS NETWORK STATE : dùng để ktra xem mạng sử dụng là mang gì nhưng bài này k dùng
    // Intent filter trong manifes dùng để lắng nghe,lọc xem các action có action nào giống action đã khai báo
             // bị tác động vào ko để thông báo
    // Nếu có nận được action thì sẽ chuyển sang class Receiver và trực tiếp gọi hàm onReceive()


    // Cách 2 :  Viết code
        /* Đăng kí sử dụng trong hàm onStart và hủy sử dụng trong onStop/Destroy. Tuy nhiên để an toàn thì đưa vào onPause

         */
        // Khi đăng kí trong manifes thì dù ứng dụng có tắt nó vẫn hoạt động, chẳng hạn ứng dụng báo thức
        // Còn đăng kí bằng code thì chỉ khi chương trình chạy nó mới có tác dụng. Hoặc khởi tạo 1 server riêng (chưa thử)
        // Chú ý nữa là luôn phải đăng kí unregister

    // Các bước để custom lại một Action theo ý muôn
    /*
    1. Vào onStart đổi addAction("tên action muốn tạo")
    2.Khi gửi intent(hàm btnsend ) cần gọi setAction để thêm action vào và gửi đi
    Ap dụng khi vd nhận được data nào đó mà muốn phát 1 action
     */
    NetworkReceiver networkReceiver;
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = findViewById(R.id.send);
        // Tự tạo action riêng trên là tunguyen.com
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("extra","Hello Tu Nguyen");
                intent.setAction("tunguyen.com");
                sendBroadcast(intent);
            }
        });
    }

    // Hàm onStart luôn được gọi đầu tiên trng vòng đời Activity
    @Override
    protected void onStart() {
        super.onStart();
        networkReceiver = new NetworkReceiver(); // Tạo receiver
        IntentFilter intentFilter  = new IntentFilter(); // Khởi tạo intentFilter
        intentFilter.addAction("tunguyen.com");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkReceiver,intentFilter); // đăng kí intent-filter
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
    }

    // Với project có 2 action là wifi thay đổi và action ấn send
    // tìm android.net.conn.CONNECTIVITY_CHANGE trên mạng do phương thức này đã cũ
}
