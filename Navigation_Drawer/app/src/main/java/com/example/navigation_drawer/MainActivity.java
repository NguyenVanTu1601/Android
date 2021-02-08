package com.example.navigation_drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

/**
 * Thêm thư viện :
 * implementation 'com.android.support:design:29.0.2'
   implementation 'com.android.support:appcompat-v7:29.0.2'

 * Các công việc cần làm :
 * Tạo xml drawermenu
 * Tạo style mới
 * Tạo nav_header.xml
 * Sửa mainactivity.xml thành DrawerLayout và thêm trc thẻ đóng android:fitsSystemWindows="true" để tạo đường kẻ ngang ngăn cách
 */

/**
 * Đọc file activity.xml
 * android:elevation="4dp" vẽ bóng dưới thành toolbar
 * tools:openDrawer="start" : mở drawer layout
 *
 * Trng class MainActivity
 * androidx.appcompat.widget.Toolbar sẽ thay thế cho v7-ToolBar
 *
 * Chú ý : android:layout_gravity="start" trong NavigationView của activity.xml có tác dụng đẩy các view vào trong, và cần thì kéo ra
 * Drawer không đóng mở được 90% do đặt nó vào trong layout nào đó, chỉ cần đưa nó ra khỏi layout là oke :))
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Anh xạ Navigation
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null){ // lưu trạng thái
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,new MessageFragment()).commit();
            navigationView.setCheckedItem(R.id.email);
        }
    }


    // Bật tắt nav
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    // Bắt sự kiện click trong navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.email:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new MessageFragment()).commit();
                break;
            case R.id.chat:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new ChatFragment()).commit();
                break;
            case R.id.profile:
                Toast.makeText(this, "Go to Profile!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send:
                Toast.makeText(this, "Go to Send!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(this, "Go to Share!", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
