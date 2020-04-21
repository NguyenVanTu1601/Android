package com.example.firebase_realtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Nhận dữ liệu và tính năng realtime của database
 * Firebase : firebaseRealtime
 * Tài khoản gg : banggia1601@gmail.com
 * Vào docx, tìm mục realtimeDatabase và lấy đường dẫn thư viện
 * Trước khi up dữ liệu cần cho phép nó đọc
 */

/**
 * Nhận dữ liệu thời gian thực thông qua addValuesEventListener
 */
/**
 *
 * Phương thức nhận dữ liệu thứ 2 : addChild(). Phương thức này ko hỗ trợ kiểu realtime
 */
public class MainActivity extends AppCompatActivity {
    DatabaseReference myData;
    TextView txtKhoaHoc;
    Button btnAndroid, btnIOS; // hai button gửi nhận dữ liệu từ app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtKhoaHoc = findViewById(R.id.textView);
        btnAndroid = findViewById(R.id.button);
        btnIOS = findViewById(R.id.button2);

        myData = FirebaseDatabase.getInstance().getReference();
        myData.child("KhoaHoc").setValue("Lập trình");


        //------------------------Dữ liệu thời gian thực--------------------------------

        // Khi dữ liệu trên node Khoa học thay đổi dữ liệu sẽ cập nhật
        myData.child("KhoaHoc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Hàm onldataChange dùng để ktra sự thay đổi dữ liệu trả về vào dataSnqpshot
                //txtKhoaHoc.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myData.child("KhoaHoc").setValue("Lập trình Android");
            }
        });

        btnIOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myData.child("KhoaHoc").setValue("Lập trình IOS");
            }
        });


        //---------------------Nhận dữ liệu với AddChild()-----------------------------
        //push dữ liệu có key = "KHoc"
        /*
        myData.child("KHoc").push().setValue("Lập trình android");
        myData.child("KHoc").push().setValue("Lập trình ios");
        myData.child("KHoc").push().setValue("Lập trình php");
        myData.child("KHoc").push().setValue("Lập trình aunity");
        myData.child("KHoc").push().setValue("Lập trình web"); */

        // Đọc dữ liệu
        myData.child("KHoc").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Hàm onChild lấy lần lượt dữ liệu của node cha(node chứa các node bé mà có key do firebase tự tạo ra)
                // Dữ liệu đọc về liên tục tới khi hết
                // txtKhoaHoc.append(dataSnapshot.getValue().toString() + "\n");

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Sự kiện này dùng để cập nhật các phần dữ liệu đã thay đổi.Khi một cái gi đó thay đổi, nó sẽ gửi về đây
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //------------------------Đọc dữ liệu Object----------------------------------------
        Phuongtien xeMay = new Phuongtien("Xe máy",2);
        Phuongtien Oto = new Phuongtien("Ô tô",2);
        Phuongtien xeDap = new Phuongtien("Xe đạp",2);

        /*
        myData.child("Phuongtien").push().setValue(xeDap);
        myData.child("Phuongtien").push().setValue(xeMay);
        myData.child("Phuongtien").push().setValue(Oto);
         */
        //Lưu ý class Phương tiện cần có contructor rỗng
        myData.child("Phuongtien").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Phuongtien xe = dataSnapshot.getValue(Phuongtien.class);
                txtKhoaHoc.append(xe.tenPhuongtien + "\n" + xe.soBanh + "\n");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*
        // -------------Đọc list lắng nghe sửa thay đổi ------------------------
        myData.child("KHoc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    txtKhoaHoc.append(data.toString());
                    // Tuy nhiên cách này mỗi lần thay đổi nó cập nhập lại toàn bộ và nói chung ko đẹp chả thèm dùng
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */
    }
}
