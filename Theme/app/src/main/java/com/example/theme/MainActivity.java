package com.example.theme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Việc thiết kết theme thường được nói riêng cho phần Activity
 * Vào res->values->styles
 * style đầu tiên có tên là AppTheme là theme mặc định, có thể tạo mới và thay thế cho nó
 * Bài này ta sẽ tạo 2 theme. 1 theme(style) cho các widget(textview...), 1 theme cho chính Activity
 * + Style cho textView (mở thẻ styles lên)
 * + Theme cho Activity
 */

/**
 * Một số thông tin cần biết trong Activity
 *  + ActionBar là phần chứa tên ứng dụng khi bật lên
 *  + Thay đổi actionBar thông qua item colorPrimary
 *  + Tên ứng dụng nằm trên actionBar được thay đổi = item : textColorPrimary
 *  + Phần hiển thị pin, giờ trên cùng thay đổi = item : colorPrimaryDark
 *  + BackGround thay đổi bằng item : windowBackground
 *  + Nền dưới cùng chỗ mấy phím home, quay lại.. thay = item : navigationBarColor
 *  + Màu cho các điểm nhấn như textView... thì gọi item : colorAccent
 */

/**
 * Cách sử dụng :
 *   + Để áp dụng style cho 1 view thì trong thẻ chứa view đó (trong thẻ xml của Activity chứa view)gọi style
 *   + Để áp dụng theme cho toàn bộ Activity của ứng dụng thì trong thẻ aplication của manifes sửa thẻ theme
 *   + Để áp dụng cho 1 Activity thì trong manifes tìm activity và thêm thẻ theme vào
 */

// Lưu ý : Do giới hạn API nên theme cho Activity chỉ chỉnh sửa trên theme có sẵn. Chưa tìm hiểu sâu xem có cách nào khác ko
// https://developer.android.com/guide/topics/ui/look-and-feel/themes#StatusBar xem tại đây
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
