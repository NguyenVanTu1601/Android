package com.example.chat_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private ImageButton SendFileButton;
    private EditText MessagesInput;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private ArrayList<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;
    private String saveCurrentTime, saveCurrentDate;
    private String checker = "", myUrl = ""; // checker : kiểm tra loại tải lên là ảnh hay pdf..., myUrl là đường dẫn của ảnh mới tải lên
    private Uri fileUri;
    private StorageTask uploadTask; // up image lên fireStorage
    private int REQUEST_CODE = 1601;

    private ProgressDialog loadingBar;
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
        DisplayLastSeen();
        SendMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessages();
            }
        });
        SendFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[] = new CharSequence[]{
                        "Images",
                        "PDF",
                        "MS Word"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                builder.setTitle("Selects the file");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            checker = "image";
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent,"Select Image"), REQUEST_CODE);

                        }
                        else if(which == 1){
                            checker = "pdf";
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/pdf");
                            startActivityForResult(Intent.createChooser(intent,"Select PDF File"), REQUEST_CODE);
                        }
                        else{
                            checker = "docx";

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/msword");
                            startActivityForResult(Intent.createChooser(intent,"Select Docx File"), REQUEST_CODE);
                        }
                    }
                });

                builder.show();
            }
        });

        onStartMess();
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
        SendFileButton = findViewById(R.id.send_file_btn);

        RootRef = FirebaseDatabase.getInstance().getReference();

        // Khởi tạo và ánh xạ recyclerView
        messageAdapter = new MessageAdapter(messagesList,this);
        userMessagesList = findViewById(R.id.private_messages_list_of_users);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);


        loadingBar = new ProgressDialog(this);
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
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
            saveCurrentTime = currentTime.format(calendar.getTime());

            // Đóng gói thông tin của tin nhắn cần gửi
            Map messageTextBody = new HashMap();
            messageTextBody.put("message", messagesText);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderID);
            messageTextBody.put("to", messageReceiverID);
            messageTextBody.put("messageID", messagePushID);
            messageTextBody.put("time", saveCurrentTime);
            messageTextBody.put("date", saveCurrentDate);

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

    //@Override
    protected void onStartMess() {
        //super.onStart();
        RootRef.child("Messages").child(messageSenderID).child(messageReceiverID)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        // đọc toàn bộ thông tin vào messages
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
        RootRef.child("Users").child(messageReceiverID).addValueEventListener(new ValueEventListener() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){

            loadingBar.setTitle("Send file...");
            loadingBar.setMessage("Vui lòng đợi trong giây lát...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            fileUri = data.getData();


            if(!checker.equals("image")){

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Document Files");

                // Tạo nhánh database dạng string chứa dữ liệu tin nhắn gửi lên (chưa tạo trên csdl)
                final String messageSenderRef = "Messages/" + messageSenderID + "/" + messageReceiverID; // nhánh người gửi
                final String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderID; // nhánh người nhân

                // Mục đích 3 dòng dưới đây là tạo ra 1 key ngẫu nhiên làm key cho đoạn tin vừa gửi lên
                DatabaseReference userMessageKeyRef = RootRef.child("Messages").child(messageSenderID)
                        .child(messageReceiverID).push();
                final String messagePushID = userMessageKeyRef.getKey();

                final StorageReference filePath = storageReference.child(messagePushID + "." + checker);

                uploadTask = filePath.putFile(fileUri);
                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Uri downloadUrl = (Uri) task.getResult();
                            myUrl = downloadUrl.toString();

                            Map messageTextBody = new HashMap();
                            messageTextBody.put("message",myUrl );
                            messageTextBody.put("name", fileUri.getLastPathSegment());
                            messageTextBody.put("type", checker);
                            messageTextBody.put("from", messageSenderID);
                            messageTextBody.put("to", messageReceiverID);
                            messageTextBody.put("messageID", messagePushID);
                            messageTextBody.put("time", saveCurrentTime);
                            messageTextBody.put("date", saveCurrentDate);

                            // Đóng gói cái gói tin trên + tạo luôn nhánh cây
                            Map messageBodyDetails = new HashMap();
                            messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody );
                            messageBodyDetails.put(messageReceiverRef + "/" + messagePushID, messageTextBody );

                            // Up lên nhánh message
                            RootRef.updateChildren(messageBodyDetails);
                            loadingBar.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingBar.dismiss();
                        Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            // up ảnh
            else if(checker.equals("image")){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Files");

                // Tạo nhánh database dạng string chứa dữ liệu tin nhắn gửi lên (chưa tạo trên csdl)
                final String messageSenderRef = "Messages/" + messageSenderID + "/" + messageReceiverID; // nhánh người gửi
                final String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderID; // nhánh người nhân

                // Mục đích 3 dòng dưới đây là tạo ra 1 key ngẫu nhiên làm key cho đoạn tin vừa gửi lên
                DatabaseReference userMessageKeyRef = RootRef.child("Messages").child(messageSenderID)
                        .child(messageReceiverID).push();
                final String messagePushID = userMessageKeyRef.getKey();

                final StorageReference filePath = storageReference.child(messagePushID + "." + "jpg");
                uploadTask = filePath.putFile(fileUri);
                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Uri downloadUrl = task.getResult();
                            myUrl = downloadUrl.toString();

                            // Đóng gói thông tin của tin nhắn cần gửi
                            Map messageTextBody = new HashMap();
                            messageTextBody.put("message", myUrl);
                            messageTextBody.put("name", fileUri.getLastPathSegment());
                            messageTextBody.put("type", checker);
                            messageTextBody.put("from", messageSenderID);
                            messageTextBody.put("to", messageReceiverID);
                            messageTextBody.put("messageID", messagePushID);
                            messageTextBody.put("time", saveCurrentTime);
                            messageTextBody.put("date", saveCurrentDate);

                            // Đóng gói cái gói tin trên + tạo luôn nhánh cây
                            Map messageBodyDetails = new HashMap();
                            messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody );
                            messageBodyDetails.put(messageReceiverRef + "/" + messagePushID, messageTextBody );

                            // Cập nhật thay đổi vào tạo nhanh cây từ gói ngay trên đây lên firebase
                            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()){
                                        loadingBar.dismiss();
                                        //Toast.makeText(ChatActivity.this, "update message success!...", Toast.LENGTH_SHORT).show();
                                    }else{
                                        loadingBar.dismiss();
                                        //Toast.makeText(ChatActivity.this, "Update messages eror...", Toast.LENGTH_SHORT).show();
                                    }
                                    MessagesInput.setText("");
                                }
                            });
                        }
                    }
                });

            }else{
                loadingBar.dismiss();
                Toast.makeText(this, "Nothing Selected, Error...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
