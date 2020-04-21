package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Tài khoản google : banggia1601@gmail.com
 * pass : banggia1601@99
 * Gần chỗ avata đăng nhập có truy cập bảng điều khiển (go to consol ) để tiến hành tạo project nhứng firebase vào
 * Database sử dụng cho bài này : firebase2019
 */

/**
 * Lưu trữ dữ liệu sử dụng setValues với String, object, map...
 * + Khi truyền 1 object thì các thuộc tính trong object phải ở dạng public, thuộc tính nào private sẽ không tìm được
 *   để truyền đi
 * + Lưu trữ sử dụng map :
 * + Lưu trữ sử dụng push : đẩy nhiều dữ liệu lên
 */

/**
 * Bắt sự kiện hoàn thành lưu dữ liệu lên database
 * Truyền thêm tham số vào setValues : new Comple...
 */

/**
 * Nhận dữ liệu bằng addValue và bắt sự kiện dữ liệu thay đổi, lập tức trả kq mới về máy
 * 
 */
public class MainActivity extends AppCompatActivity {
    DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //------------------------Truyền dữ liệu---------------------------------------------------

        // Khơi tạo database
        //Khi gọi tới myDatabase tức là gọi tới node gốc trong firebase
        myDatabase = FirebaseDatabase.getInstance().getReference();

        // SetValues(String)
        myDatabase.child("Hoten").setValue("Nguyễn Văn Tú"); // tạo node con. Nếu node đã tồn tại sẽ xóa giá trị và gán lại

        // setValues(Object)
        Sinhvien sv = new Sinhvien("Nguyễn Văn Tài","Thái Bình",2003);
        myDatabase.child("SinhVien").setValue(sv);

        // setValues(map)
        Map<String, Integer> myMap = new HashMap<>();
        myMap.put("Xemay",2);
        myMap.put("Oto",4);
        myDatabase.child("Phuongtien").setValue(myMap);

        //Push : đẩy dữ liệu lên với key tự động do firebase tạo
        Sinhvien sinhvien = new Sinhvien("Nguyễn Văn Tú","Thái Bình",1999);
        Sinhvien sinhvien1 = new Sinhvien("Nguyễn Đức Nam","Thái Bình",1999);
        myDatabase.child("HocVien").push().setValue(sinhvien);

        // Bắt sự kiện hoàn thành việc setValues
        myDatabase.child("TuNguyen").setValue("Lập trình android", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                // Biếm databaseError có thể dùng. Nếu lưu xảy ra lỗi thì dataError sẽ báo lỗi
                // Nếu hoàn tất việc lưu (khác với lưu thành công hoàn toàn)thì databaseError = null

                if(databaseError == null){
                    Toast.makeText(MainActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Xảy ra lỗi" + databaseError.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //-----------------------------Nhận dữ liệu------------------------------------------------------

    }

}
