package com.example.videomeetingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videomeetingapp.R;
import com.example.videomeetingapp.models.User;
import com.example.videomeetingapp.network.ApiClient;
import com.example.videomeetingapp.network.ApiService;
import com.example.videomeetingapp.utilities.Constants;
import com.example.videomeetingapp.utilities.PreferenceManager;
import com.google.firebase.iid.FirebaseInstanceId;


import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutgoingInvitationActivity extends AppCompatActivity {
    private ImageView imageMeetingType, imageStopInvitation;
    private TextView textFirstChar, textName, textEmail;
    private PreferenceManager preferenceManager;
    private String inviterToken = null; // token người mời (token bản thân)
    private String meetingRoom = null; // dùng cho Jetsi meet
    private String meetingType = null; // type = audio/video
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_invitation);

        init();

        meetingType = getIntent().getStringExtra("type");

        if (meetingType != null){
            if (meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.ic_videocam);
            }else if (meetingType.equals("audio")){
                imageMeetingType.setImageResource(R.drawable.ic_call);
            }
        }

        User user = (User) getIntent().getSerializableExtra("user");
        if (user != null){
            textFirstChar.setText(user.firstName.substring(0,1));
            textName.setText(user.firstName + " " + user.lastName);
            textEmail.setText(user.email);
        }

        imageStopInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
                if (user != null){
                    cancelInvitation(user.token);
                }
            }
        });

        // gọi method
        // get inviterToken : inviter - người mời
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null){
                inviterToken = task.getResult().getToken();
                if (meetingType != null && user != null){
                    initiateMeeting(meetingType, user.token);
                }
            }
        });

    }

    private void init() {
        imageMeetingType    = findViewById(R.id.imageMeetingType);
        imageStopInvitation = findViewById(R.id.imageStopInvitation);
        textFirstChar       = findViewById(R.id.textFirstChar);
        textName            = findViewById(R.id.textUserName);
        textEmail           = findViewById(R.id.textEmail);
        preferenceManager   = new PreferenceManager(getApplicationContext());
    }

    // Put thông tin lên FCM để chuyển vể MessagingService của bên nhận
    private void initiateMeeting(String meetingType, String receiverToken){
        // initiate : khởi xướng, khởi tạo
        // inviter : người mời
        // receiverToken : Token của người sẽ nhận cuộc gọi lấy từ user.token
        try {

            // tạo khung body
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            // thông tin cơ bản
            data.put(Constants.REMOTE_MSG_TYPE, Constants.REMOTE_MSG_INVITATION); // dùng để xác nhận bên Service đang nhận tin yêu cầu kết nối
            data.put(Constants.REMOTE_MSG_MEETING_TYPE, meetingType); // loại cuộc gọi là audio hay video
            data.put(Constants.KEY_FIRST_NAME, preferenceManager.getString(Constants.KEY_FIRST_NAME));
            data.put(Constants.KEY_LAST_NAME, preferenceManager.getString(Constants.KEY_LAST_NAME));
            data.put(Constants.KEY_EMAIL, preferenceManager.getString(Constants.KEY_EMAIL));
            data.put(Constants.REMOTE_MSG_INVITER_TOKEN, inviterToken);

            // thông tin về room meeting để dùng jitsi meet, truyền key room vào MessagingService và truyền sang Incoming
            // 2 bên in, out thống nhất key thì mới kết nối được
            meetingRoom = preferenceManager.getString(Constants.KEY_USER_ID) + "_"+
                    UUID.randomUUID().toString().substring(0,5);
            //Toast.makeText(this, "meetingRoom : " + meetingRoom, Toast.LENGTH_SHORT).show();
            data.put(Constants.REMOTE_MSG_MEETING_ROOM, meetingRoom);

            // body
            body.put(Constants.REMOTE_MSG_DATA, data);
            body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);
            // Phần body này tham chiếu thông tin giống bên file Learn, body là dạng JSON Object....


            // send body with retrofit
            sendRemoteMessage(body.toString(), Constants.REMOTE_MSG_INVITATION);




        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // sendRemote : gửi từ xa
    // Chịu trách nhiệm gửi thông tin ng đang gọi và yêu cầu kết nối tới người nhận
    private void sendRemoteMessage(String remoteMessageBody, String type){
        ApiClient.getClient().create(ApiService.class) // getClient() từ ApiClient class
                .sendRemoteMessage(Constants.getRemoteMessageHeaders(), remoteMessageBody) // method này ở interface ApiService, remoteMeBody là tham số của fun
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse( @NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.isSuccessful()){
                            if (type.equals(Constants.REMOTE_MSG_INVITATION)){ // type là tham số của function
                                Toast.makeText(OutgoingInvitationActivity.this, "Invitation send success!", Toast.LENGTH_SHORT).show();
                            }else if (type.equals(Constants.REMOTE_MSG_INVITATION_CANCELLED)){ // type là tham số của function
                                Toast.makeText(OutgoingInvitationActivity.this, "Cancel Invitation!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }else{
                            Toast.makeText(OutgoingInvitationActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Toast.makeText(OutgoingInvitationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }

    // Người gọi hủy kết nối
    private void cancelInvitation(String receiverToken){
        // receiverToken là token của người gửi yêu cầu kết nối tới
        // token này được nhận qua intent từ service còn service nhận token này thông qua gói tin
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put(Constants.REMOTE_MSG_TYPE, Constants.REMOTE_MSG_INVITATION_RESPONSE);
            // do nếu ng gửi hủy yêu cầu kết nối thì cũng hiển thị trên màn hình ng nhận. Do đó type bản tin này cũng là response,
            // mục tiêu là response lại cho ng nhận rằng ng gửi đã gửi yêu cầu

            data.put(Constants.REMOTE_MSG_INVITATION_RESPONSE, Constants.REMOTE_MSG_INVITATION_CANCELLED); // yêu cầu hủy kết nối

            body.put(Constants.REMOTE_MSG_DATA, data);
            body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);

            sendRemoteMessage(body.toString(), Constants.REMOTE_MSG_INVITATION_CANCELLED); // trong video là ...REMOTE_.._RESPONSE

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    // Broadcast dùng lắng nghe từ phía MessagingService xem người được mời video call có đồng ý hay hủy bỏ kết nối
    private BroadcastReceiver invitationResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra(Constants.REMOTE_MSG_INVITATION_RESPONSE);
            if (type != null){
                if (type.equals(Constants.REMOTE_MSG_INVITATION_ACCEPTED)){
                    Toast.makeText(context, "User accept invitation!", Toast.LENGTH_SHORT).show();

                    // sử dụng jitsi meet
                    URL serverURL = null;
                    try {
                        serverURL = new URL("https://meet.jit.si");

                        // chung cả audio và video
                        JitsiMeetConferenceOptions.Builder builder = new JitsiMeetConferenceOptions.Builder();
                        builder.setServerURL(serverURL);
                        builder.setWelcomePageEnabled(false);
                        builder.setRoom(meetingRoom);

                        // của audio
                        if (meetingType.equals("audio")){
                            builder.setVideoMuted(true);
                        }

                        JitsiMeetActivity.launch(OutgoingInvitationActivity.this, builder.build());
                        finish(); // bắt buộc phải có finish ở đây, nếu ko lỗi sml nhá :D

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }


                }else if (type.equals(Constants.REMOTE_MSG_INVITATION_REJECTED)){
                    Toast.makeText(context, "User reject invitation!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    };

    // Đăng ký cho Broadcast
    @Override
    protected void onStart() {
        super.onStart();
        // đăng ký
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                invitationResponseReceiver,
                new IntentFilter(Constants.REMOTE_MSG_INVITATION_RESPONSE)
        );

    }

    // Hủy Broadcast
    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(invitationResponseReceiver);

    }
}
