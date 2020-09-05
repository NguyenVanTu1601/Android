package com.example.chat_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText userMessageInput;
    private ImageButton sendMessageButton;
    private ScrollView mScrollView;
    private TextView displayTextMessage;
    private String currenGroupName, currenUserID, currenUserName;
    private String saveCurrentDate, saveCurrentTime; // lưu thời gian gửi tin nhắn
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, GroupNameRef, GroupMessageKeyRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        // Tạo nút quay về trên toolBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        currenGroupName = intent.getStringExtra("groupName");

        InitializeFiled();

        // Tạo toolBar
        CreateToolBar(currenGroupName);

        // Lấy thông tin về tên, ID ng dùng
        GetUserInfo();

        // Hiển thị tin nhắn
        ShowChangeMessage();

        // Sự kiện gửi tin nhắn
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMessageInfoToDataBase();
                userMessageInput.setText("");
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });



    }
    // Khởi tạo
    private void InitializeFiled() {
        userMessageInput = findViewById(R.id.input_group_message);
        sendMessageButton = findViewById(R.id.send_message_button);
        mScrollView = findViewById(R.id.my_scroll_view);
        displayTextMessage = findViewById(R.id.group_chat_text_display);
        toolbar = findViewById(R.id.group_chat_bar_layout);
        mAuth = FirebaseAuth.getInstance();
        currenUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users"); // Nhánh user
        GroupNameRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(currenGroupName); // Nhánh có tên là tên group
    }

    // Lưu tin nhắn vào database
    private void SaveMessageInfoToDataBase() {
        String message = userMessageInput.getText().toString();
        String messageKey = GroupNameRef.push().getKey(); // Tạo ra 1 key bất kì trong nhánh là tên group
        if(!TextUtils.isEmpty(message)){

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
            saveCurrentTime = currentTime.format(calendar.getTime());

            // Lấy ra nhánh con của group có tên = key đã tạo bên trên
            GroupMessageKeyRef = GroupNameRef.child(messageKey);

            // Lưu tin mới vào nhánh con kia
            HashMap<String,Object> messageInfoMap = new HashMap<>();
                messageInfoMap.put("name",currenUserName);
                messageInfoMap.put("message",message);
                messageInfoMap.put("date",saveCurrentDate);
                messageInfoMap.put("time",saveCurrentTime);
            GroupMessageKeyRef.updateChildren(messageInfoMap);
        }
    }

    private void GetUserInfo() {
        UsersRef.child(currenUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    currenUserName = dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Tạo toolBar
    private void CreateToolBar(String groupName) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(groupName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    // Bắt sự kiện thay đổi tin nhắn

    protected void ShowChangeMessage() {
        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        GroupNameRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessage(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessage(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessage(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Hiển thị tin nhắn
    private void DisplayMessage(DataSnapshot dataSnapshot) {

        Iterator iterator = dataSnapshot.getChildren().iterator();
        while (iterator.hasNext()){
            String chatDate = ((DataSnapshot) iterator.next()).getValue().toString();
            String chatMessage = ((DataSnapshot) iterator.next()).getValue().toString();
            String chatName = ((DataSnapshot) iterator.next()).getValue().toString();
            String chatTime = ((DataSnapshot) iterator.next()).getValue().toString();
            displayTextMessage.append(chatName + "\n" + chatMessage
            + "\n" + chatTime + "     " + chatDate + "\n\n");

            // Scroll để tin nhắn luôn hiện thêm trong trong sự kiên gửi tin
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

}
