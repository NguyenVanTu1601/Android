package com.example.fragment_dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Do chỉ là daialog của hộp thoại xác nhận nên không cần layout cũng ko cần gọi onCreateView mà
 * gọi onCreateDialog
 */
public class MainActivity extends AppCompatActivity implements Delete{
    Button btnXoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnXoa = findViewById(R.id.button);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Dialog fragment_dialog = new Fragment_Dialog();
                fragment_dialog.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    @Override
    public void GiatriXoa(boolean delete) {
        if(delete){
            Toast.makeText(this, "Bạn chọn xóa!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Không xóa!", Toast.LENGTH_SHORT).show();
        }
    }
}
