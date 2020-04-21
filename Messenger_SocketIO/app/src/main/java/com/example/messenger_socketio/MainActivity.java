package com.example.messenger_socketio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ArrayList<User> listOnline;
    private ArrayList<String> listMessenger;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListOnline();
        initListMessenger();
        initNavigation();

        Intent intent = getIntent();
        String username = intent.getStringExtra("User-connection");

        Connect_SocketIO connect_socketIO = new Connect_SocketIO(this);
        connect_socketIO.connectSocketIO();
        connect_socketIO.sendUser(username);
        connect_socketIO.sendMessenger("Hello Tú nguyễn");
        connect_socketIO.getMessenger();
    }

    private void initListOnline(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerDanhSachOnline);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setDataListOnline();
        ListOnline_Adapter listOnline_adapter = new ListOnline_Adapter(getApplicationContext(),R.layout.list_online,listOnline);
        recyclerView.setAdapter(listOnline_adapter);
    }

    private void initListMessenger(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerMessenger);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setMessenger();
        ListMessenger_Adapter adapter = new ListMessenger_Adapter(this,R.layout.list_messenger, listMessenger);
        recyclerView.setAdapter(adapter);
    }

    private void setDataListOnline(){
        listOnline = new ArrayList<>();
        listOnline.add(new User("Nguyễn Văn Tú"));
        listOnline.add(new User("Nguyễn Văn Tài"));
        listOnline.add(new User("Phùng Đình Tùng"));
        listOnline.add(new User("Trịnh Đình Trung"));
        listOnline.add(new User("Hàn Nhật Tuấn"));
        listOnline.add(new User("Hàn Thiên Nhu"));
    }
    private void setMessenger(){
        listMessenger = new ArrayList<>();
        listMessenger.add("Hello!");
        listMessenger.add("Hi Tú!");
        listMessenger.add("Dạo này thế nào!");
        listMessenger.add("Dạo này vẫn khỏe. Còn bạn!");
        listMessenger.add("Mọi thứ đều ổn cả!");
        listMessenger.add("Vẫn đang ở nhà chứ");
        listMessenger.add("Ư vẫn ở nhà chơi thôi");
        listMessenger.add("OKE mai t xuống chơi");

    }

    private void initNavigation(){
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,null,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    // Click item menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.email:
                Toast.makeText(this, "Go to email!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.chat:
                Toast.makeText(this, "Go to chat!", Toast.LENGTH_SHORT).show();
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
