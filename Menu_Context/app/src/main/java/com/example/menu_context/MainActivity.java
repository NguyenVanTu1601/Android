package com.example.menu_context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnColor;
    ConstraintLayout manHinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnColor = (Button)findViewById(R.id.buttonColor);
        manHinh = (ConstraintLayout) findViewById(R.id.manhinh);
        // Đăng kí view cho contextmenu _ menu cần tác động tới
        registerForContextMenu(btnColor);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        // Tạo tên , icon
        menu.setHeaderTitle("Chọn màu");
        menu.setHeaderIcon(R.mipmap.ic_launcher);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    // Bắt sự kiện

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.maudo:
                manHinh.setBackgroundColor(Color.RED);
                break;
            case R.id.mauvang:
                manHinh.setBackgroundColor(Color.YELLOW);
                break;
            case R.id.mauxanh:
                manHinh.setBackgroundColor(Color.GREEN);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
