package com.example.mvp_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

/**
 * MVP : Model- View - Presenter
 * Model : Nơi xử lý logic, xử lý nghiệp vụ cho chức năng, app
 * View : Hiển thị thông tin cho ng dùng nhìn thấy : Activity, fragment, view
 * Presenter : Là trung gian để giao tiếp giữa view và model
 */

/**
 * Thực hiện với project này :
 * + Từ fragment truyền yêu cầu tới Presenter . Từ Pre truyền tới Model thông qua khởi tao new... và gọi hàm
 * + Từ model trả kq có login hay k vào Presenter thông qua Interface ModelResponse
 * + Từ Presenter trả kq về cho view khi login được thì hiển thị gì thông qua interfave ViewLoginListener
 * + Từ view trả về cho frament thông qua implement interface
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentLogin);

    }
}
