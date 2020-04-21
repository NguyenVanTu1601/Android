package com.example.menu_popup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.buttonMenu);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonShowMenu();
            }
        });
    }
    private void buttonShowMenu(){
        PopupMenu popupMenu = new PopupMenu(this, btn);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.them:
                        Toast.makeText(MainActivity.this, "Bạn chọn thêm", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.xoa:
                        Toast.makeText(MainActivity.this, "Bạn chọn xóa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sua:
                        Toast.makeText(MainActivity.this, "Bạn chọn sửa", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
