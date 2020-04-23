package com.example.chat_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private String messageReceiverID; // id người nhận tin nhắn
    private String messageSenderID;     // id bản thân
    private String messageReceiverName, messageReceiverImage;
    private TextView userName, userLastSeen;
    private CircleImageView userImage;
    private Toolbar ChatToolbar;
    private ImageButton SendMessagesButton;
    private EditText MessagesInput;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private ArrayList<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageReceiverID = getIntent().getStringExtra("visit_user_id");
        messageReceiverName = getIntent().getStringExtra("visit_user_name");
        messageReceiverImage = getIntent().getStringExtra("visit_image");

        IntializeControlers();

        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();

        userName.setText(messageReceiverName);
        Picasso.with(this).load(messageReceiverImage).placeholder(R.drawable.profile_image)
        .into(userImage);

        SendMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessages();
            }
        });

    }



    private void IntializeControlers() {

        // Tạo toolbar
        ChatToolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(ChatToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        // Anh xạ actionbar
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = inflater.inflate(R.layout.custom_chat_bar,null);
        actionBar.setCustomView(actionBarView);

        // ánh xạ thuộc tính trong actionbar
        userImage = findViewById(R.id.custom_profile_image);
        userName = findViewById(R.id.custom_profile_name);
        userLastSeen = findViewById(R.id.custom_user_last_seen);

        SendMessagesButton = findViewById(R.id.send_message_btn);
        MessagesInput = findViewById(R.id.input_messages);

        RootRef = FirebaseDatabase.getInstance().getReference();

        // Khởi tạo và ánh xạ recyclerView
        messageAdapter = new MessageAdapter(messagesList,this);
        userMessagesList = findViewById(R.id.private_messages_list_of_users);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);

    }

    private void SendMessages() {
        String messagesText = MessagesInput.getText().toString();
        if(!TextUtils.isEmpty(messagesText)){

            // Tạo nhánh database dạng string chứa dữ liệu tin nhắn gửi lên (chưa tạo trên csdl)
            String messageSenderRef = "Messages/" + messageSenderID + "/" + messageReceiverID; // nhánh người gửi
            String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderID; // nhánh người nhân

            // Mục đích 3 dòng dưới đây là tạo ra 1 key ngẫu nhiên làm key cho đoạn tin vừa gửi lên
            DatabaseReference userMessageKeyRef = RootRef.child("Messages").child(messageSenderID)
                    .child(messageReceiverID).push();
            String messagePushID = userMessageKeyRef.getKey();

            // Đóng gói thông tin của tin nhắn cần gửi
            Map messageTextBody = new HashMap();
            messageTextBody.put("message", messagesText);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderID);

            // Đóng gói cái gói tin trên + tạo luôn nhánh cây
            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody );
            messageBodyDetails.put(messageReceiverRef + "/" + messagePushID, messageTextBody );

            // Cập nhật thay đổi vào tạo nhanh cây từ gói ngay trên đây lên firebase
            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        //Toast.makeText(ChatActivity.this, "update message success!...", Toast.LENGTH_SHORT).show();
                    }else{
                        //Toast.makeText(ChatActivity.this, "Update messages eror...", Toast.LENGTH_SHORT).show();
                    }
                    MessagesInput.setText("");
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        RootRef.child("Messages").child(messageSenderID).child(messageReceiverID)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Messages messages = dataSnapshot.getValue(Messages.class);
                        messagesList.add(messages);
                        messageAdapter.notifyDataSetChanged();

                        // Trượt recyclerview xuống tin nhắn mới nhất
                        userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
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
    }

    private void DisplayLastSeen(){
        RootRef.child("Users").child(messageSenderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Kiểm tra hiển thị trạng thái online / off
                if(dataSnapshot.child("userState").hasChild("state")){
                    String state = dataSnapshot.child("userState").child("state").getValue().toString();
                    String date = dataSnapshot.child("userState").child("date").getValue().toString();
                    String time = dataSnapshot.child("userState").child("time").getValue().toString();

                    if(state.equals("online")){
                        userLastSeen.setText("online");
                    }else if (state.equals("offline")){
                        userLastSeen.setText("Last seen : " + date + " " + time);
                    }
                }else{
                    userLastSeen.setText("offline");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
