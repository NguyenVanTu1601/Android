package com.example.chat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabsAcessorAdapter myTabsAcessorAdapter;
    private FirebaseUser currenUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private String currentUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();

        // Tạo toolbar
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Chat App");

        // tạo viewPager và thêm tablayout vào
        myViewPager.setAdapter(myTabsAcessorAdapter);
        myTabLayout.setupWithViewPager(myViewPager);

        //
        mAuth = FirebaseAuth.getInstance();
        currenUser = mAuth.getCurrentUser();
        UpdateUserStatus("online");
    }

    private void Anhxa() {
        myToolbar              = findViewById(R.id.main_page_toolbar);
        myViewPager            = findViewById(R.id.main_tabs_pager);
        myTabLayout            = findViewById(R.id.main_tabs);
        myTabsAcessorAdapter   = new TabsAcessorAdapter(getSupportFragmentManager());
        currenUser = null;
        RootRef = FirebaseDatabase.getInstance().getReference();
    }

    // Bắt đầu chạy ktra xem đăng nhập chưa, chưa thì quay lại màn hình đăng nhập
    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        currenUser = mAuth.getCurrentUser();
        if (currenUser == null){
            SentUserToLoginActivity();
        }else{
            UpdateUserStatus("online");
            VeriFyUserExistance();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth = FirebaseAuth.getInstance();
        currenUser = mAuth.getCurrentUser();
        if(currenUser != null){
            UpdateUserStatus("offline");
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mAuth = FirebaseAuth.getInstance();
//        currenUser = mAuth.getCurrentUser();
//        if(currenUser != null){
//            UpdateUserStatus("offline");
//        }
//    }

    // Kiểm tra thông tin ng dùng chưa có thì chuyển sang form setting
    private void VeriFyUserExistance() {
        // Vào database -> user . kiểm tra user đã đăng nhập thông qua uID
        // Nếu user có dữ liệu thì thôi, chưa có thì sang setting thêm dữ liệu vào
        String currenUserID = mAuth.getCurrentUser().getUid();
        RootRef.child("Users").child(currenUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Nếu đã có cột tên là name thì thông báo lời chào
                if(dataSnapshot.child("name").exists()){
                }
                // Chưa có thì sang setting để thêm
                else{
                    SendUserToSettingActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    // Đi tới màn hình login
    private void SentUserToLoginActivity() {
        Intent intent = new Intent(MainActivity.this,Login_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Đi tới màn hình setting
    private void SendUserToSettingActivity(){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);

    }
    // Tạo menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Bắt sự kiện trên menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_find_friends_option:
                startActivity(new Intent(MainActivity.this, FindFriendsActivity.class));
                break;
            case R.id.main_settings_option:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
            case R.id.main_logout_option:
                UpdateUserStatus("offline");
                mAuth.signOut();
                SentUserToLoginActivity();
                break;
            case R.id.main_create_group_option:
                RequestNewGroup();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Tạo group chat mới
    private void RequestNewGroup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Group name : ");

        final EditText groupNameField = new EditText(this);
        groupNameField.setHint("e.g Coding Cofe");

        builder.setView(groupNameField);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName = groupNameField.getText().toString();
                if(TextUtils.isEmpty(groupName)){
                    Toast.makeText(MainActivity.this, "Nhập tên group", Toast.LENGTH_SHORT).show();

                }else{
                    CreateNewGroup(groupName);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void CreateNewGroup(final String groupName) {
        RootRef.child("Groups").child(groupName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Tạo thành công group " + groupName, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // bấm vào nút back cứng trên đt
    @Override
    public void onBackPressed() {
        mAuth = FirebaseAuth.getInstance();
        currenUser = mAuth.getCurrentUser();
        if(currenUser != null){

        }else{
            super.onBackPressed();
        }
    }


    // Cập nhật trạng thái user online/ offline
    private void UpdateUserStatus(String state){
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time", saveCurrentTime);
        onlineStateMap.put("date",saveCurrentDate);
        onlineStateMap.put("state", state);

        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef.child("Users").child(currentUserID).child("userState")
                .updateChildren(onlineStateMap);
    }
}
