package com.example.ripple_custombutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Thẻ ripple dùng để tạo hiệu ứng gợn sóng cho button từ phiên bản 5.0
 * Để dùng thì vào drawable tạo 1 file xml sau đó theo gợi ý custom sang v21
 * Tuy nhiên nếu máy dưới 5.0 thì tác dụng của thẻ ko còn do đó để đẹp thì trong mục thẻ ko phải v21
 * ta cũng tạo 1 custom tương tự chỉ ko có ripple thôi mà thay vào đó là 2 item để đổi màu.
 */
public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        txt = findViewById(R.id.textView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setText("Wellcome to Android!");
            }
        });
    }
}
