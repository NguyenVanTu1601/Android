package com.example.fragment_bt_xu_ly_giao_dien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Tạo interface truyền sinh viên . Interface này sẽ có tác dụng truyền  1 sinh viên từ list về Main để từ main đưa lên info
 */
public class MainActivity extends AppCompatActivity implements TruyenSinhVien{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // truyền thông tin nhận được sang info
    @Override
    public void DataStudent(SinhVien sinhVien) {
        Fragment_student_info fragment_student_info = (Fragment_student_info) getSupportFragmentManager().findFragmentById(R.id.fragment_info);
        // Kiểm tra hướng màn hình
        Configuration configuration = getResources().getConfiguration();


        // Kiểm tra điều kiện để chuyển màn hình
        if(fragment_student_info != null && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){ // Nếu đang xoay ngang vì chỉ xoay ngang mới có giá trị chuyển tiếp như kia
            // Nếu nó không null và nó trong layout (&& fragment_student_info.isInLayout())
            fragment_student_info.setInfo(sinhVien);
        }
        else{
            Intent intent = new Intent(MainActivity.this, Student_Infomation_Activity.class);
            intent.putExtra("info", sinhVien);
            startActivity(intent);
        }


    }
}
