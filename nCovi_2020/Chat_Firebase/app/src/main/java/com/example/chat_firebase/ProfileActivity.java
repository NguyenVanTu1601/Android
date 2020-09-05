package com.example.chat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private String receiverUserID; // id của người mình đang click vào xem
    private String currentStatus;  // trạng thái xem đã gửi tin bao giờ chưa
    String senderUserID; // ID của bản thân mình

    private CircleImageView userProfileImage;
    private TextView userProfileName, userProfileStatus;
    private Button SendMessageRequestButton;    // gửi yêu cầu chát tới đối phương
    private Button DeclineMessageRequestButton; // button dùng để hủy yêu cầu chat của đối phương
    private DatabaseReference UserRef; // Lấy
    private DatabaseReference chatRequestRef; // Dùng tạo nhánh mới chứa vấn đề về gửi nhận yêu cầu chat
    private FirebaseAuth mAuth;               // Lấy id bản thân
    private DatabaseReference ContactsRef;    // Nhánh mới chứa vấn đề nội dung chat cá nhân
    private DatabaseReference NotificationRef; // Chứa các notifi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        receiverUserID = getIntent().getStringExtra("visit_user_id");

        InitializeField();

        RetrieveUserInfo();

    }

    private void RetrieveUserInfo() {
        UserRef.child(receiverUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Đọc dự liệu từ firebase để hiện lên
                if(dataSnapshot.exists() && (dataSnapshot.hasChild("image"))){
                    String userImage = dataSnapshot.child("image").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();
                    String userStatus = dataSnapshot.child("status").getValue().toString();

                    Picasso.with(ProfileActivity.this).load(userImage).
                            placeholder(R.drawable.profile_image).into(userProfileImage);
                    userProfileName.setText(userName);
                    userProfileStatus.setText(userStatus);

                    ManageChatRequest();
                }else{
                    String userName = dataSnapshot.child("name").getValue().toString();
                    String userStatus = dataSnapshot.child("status").getValue().toString();

                    userProfileName.setText(userName);
                    userProfileStatus.setText(userStatus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Kiểm tra các trường hợp gửi tin
    private void ManageChatRequest() {

        // Kiểm tra đã tạo nhánh cây từ phía mình chưa
        // chưa tức là đã gửi yêu cầu chat chưa để thay đổi biến curentStatus để biết có chat hay k

        chatRequestRef.child(senderUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Kiểm tra nhánh của user vừa chọn trong nhánh của mình nếu đã tồn tại
                        if(dataSnapshot.hasChild(receiverUserID)){
                            String request_type = dataSnapshot.child(receiverUserID).child("request_type")
                                    .getValue().toString();
                            // Nếu trạng thái là đang gửi yêu cầu cho ngta
                            if(request_type.equals("sent")){
                                currentStatus = "request_sent";
                                SendMessageRequestButton.setText("Cancel Chat Request");
                            }
                            // Nếu trạng thái là nhận tức là đã nhận được yêu cầu chat và xem xem có chấp nhận không
                            if(request_type.equals("received")){
                                currentStatus = "request_received";
                                SendMessageRequestButton.setText("Accept Chat Request");
                                DeclineMessageRequestButton.setVisibility(View.VISIBLE); // hiện button hủy xác nhận yêu cầu chat
                                DeclineMessageRequestButton.setEnabled(true);
                                DeclineMessageRequestButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        CancelChatRequest();
                                    }
                                });
                            }

                        }

                        // Nếu nhánh chứa user vừa chọn chưa có -> có 2 khả năng : 1 là đã thành friend, 2 là chưa tương tác
                        else{
                            // kiểm tra đã là friend chưa thông qua cây Contacts
                            // Dùng Forsingle do cái ktra này chỉ dùng 1 lần khi mở tab. Do đã là bạn r thì k thay đổi nữa
                            // Còn ValuesEventListener là lắng nghe mỗi khi có thay đổi.
                            ContactsRef.child(senderUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // Kiểm tra nếu đã có nhánh cây thì đổi trạng thái currentStatus thành friends
                                    if(dataSnapshot.hasChild(receiverUserID)){
                                        currentStatus = "friends";
                                        SendMessageRequestButton.setText("Remove this Contact");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        // So sánh id ng click vào user và id của mình nếu khác thì tiếp tục
        if(!senderUserID.equals(receiverUserID)){
            SendMessageRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendMessageRequestButton.setEnabled(false); // ko thể bấm nữa
                    // Nếu trạng thái là new -> chưa gửi yêu cầu chat -> cần gửi yêu cầu
                    if(currentStatus.equals("new")){
                        SendChatRequest();
                    }

                    // Nếu trạng thái là request_sent : đã gửi yêu cầu chat -> có thể hủy yêu cầu chat khi click button
                    // Button trở thành button hủy yêu cầu
                    if(currentStatus.equals("request_sent")){
                        CancelChatRequest();
                    }

                    // Nếu trạng thái là nhận được yêu cầu thì xử lý button này như nào :)) -> có thể chấp nhận hoặc không
                    // Button trở thành chấp nhận yêu cầu
                    if(currentStatus.equals("request_received")){
                        AcceptChatRequest();
                    }

                    // Nếu đã được chấp nhận và trở thành bạn
                    // Button trở thành remove chat nếu muốn hủy kb
                    if(currentStatus.equals("friends")){
                        RemoveSpecificContact();
                    }
                }
            });
        }else{
            SendMessageRequestButton.setVisibility(View.INVISIBLE);
        }
    }

    // Nếu đã là bạn và muốn xóa kết bạn
    private void RemoveSpecificContact() {
        ContactsRef.child(senderUserID).child(receiverUserID).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            ContactsRef.child(receiverUserID).child(senderUserID).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                SendMessageRequestButton.setEnabled(true);
                                                currentStatus = "new";
                                                SendMessageRequestButton.setText("Send Message");

                                                // Ẩn button
                                                DeclineMessageRequestButton.setVisibility(View.INVISIBLE);
                                                DeclineMessageRequestButton.setEnabled(false);

                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    // Chấp nhận kết bạn
    private void AcceptChatRequest() {
        ContactsRef.child(senderUserID).child(receiverUserID)
                .child("Contacts").setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    ContactsRef.child(receiverUserID).child(senderUserID)
                            .child("Contacts").setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                chatRequestRef.child(senderUserID).child(receiverUserID)
                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            chatRequestRef.child(receiverUserID).child(senderUserID)
                                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    SendMessageRequestButton.setEnabled(true);
                                                    currentStatus = "friends";
                                                    SendMessageRequestButton.setText("Remove this contacts");
                                                    DeclineMessageRequestButton.setVisibility(View.INVISIBLE);
                                                    DeclineMessageRequestButton.setEnabled(false);

                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    // Hủy bỏ yêu cầu chat, xóa các nhánh cây, cập nhật lại text của button
    private void CancelChatRequest() {
        chatRequestRef.child(senderUserID).child(receiverUserID).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            chatRequestRef.child(receiverUserID).child(senderUserID).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                SendMessageRequestButton.setEnabled(true);
                                                currentStatus = "new";
                                                SendMessageRequestButton.setText("Send Message");

                                                // Ẩn button
                                                DeclineMessageRequestButton.setVisibility(View.INVISIBLE);
                                                DeclineMessageRequestButton.setEnabled(false);

                                            }
                                        }
                                    });
                        }
                    }
                });
    }


    // Gửi yêu cầu tạo nhánh cây mới:D nếu chưa chat
    private void SendChatRequest() {
        // Bên gửi lưu 1 nhánh là mình gưi đi
        chatRequestRef.child(senderUserID).child(receiverUserID)
                .child("request_type").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // bên nhận lưu 1 nhánh dữ liệu mà mình nhận về :))
                        chatRequestRef.child(receiverUserID).child(senderUserID)
                                .child("request_type").setValue("received")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    // Tạo notification
                                    HashMap<String, String> chatNotificationMap = new HashMap<>();
                                    chatNotificationMap.put("from", senderUserID);
                                    chatNotificationMap.put("type","request");
                                    // Tạo nhánh cây có key random
                                    NotificationRef.child(receiverUserID).push().setValue(chatNotificationMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                // Hiển thị lại nút bấm và trạng thái để hoạt động nút bấm
                                                SendMessageRequestButton.setEnabled(true);
                                                currentStatus = "request_sent";
                                                SendMessageRequestButton.setText("Cancel chat request");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
    }


    private void InitializeField() {
        userProfileImage = findViewById(R.id.visit_profile_image);
        currentStatus = "new";
        userProfileName = findViewById(R.id.visit_user_name);
        userProfileStatus = findViewById(R.id.visit_profile_status);
        SendMessageRequestButton = findViewById(R.id.send_message_request_button);
        DeclineMessageRequestButton = findViewById(R.id.decline_message_request_button);
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        senderUserID = mAuth.getCurrentUser().getUid();
        chatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests"); // tạo nhánh mới của database
        ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        NotificationRef = FirebaseDatabase.getInstance().getReference().child("Notifications");
    }


}
