package com.example.bottom_app_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * + Thêm thư viện vào module app
 * + Sửa file style thành MaterialComponents
 * + Sang file layout dùng CoordinatorLayout
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Click button Add", Toast.LENGTH_SHORT).show();
            }
        });

        BottomAppBar appBar = findViewById(R.id.bottomAppBar);
        appBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuSetting:
                        Toast.makeText(MainActivity.this, "Click icon Setting", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuAbout:
                        Toast.makeText(MainActivity.this, "Click icon About", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

    }

}
