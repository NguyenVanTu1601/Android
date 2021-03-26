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
import com.example.videomeetingapp.network.ApiClient;
import com.example.videomeetingapp.network.ApiService;
import com.example.videomeetingapp.utilities.Constants;
import com.example.videomeetingapp.utilities.PreferenceManager;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomingInvitationActivity extends AppCompatActivity {

    private ImageView imageMeetingType, imageRejectInvitation, imageAcceptInvitation;
    private TextView textFirstChar, textName, textEmail;
    private PreferenceManager preferenceManager;
    private String meetingType = null;
    private String meetingRoom = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_invitation);

        init();

        meetingType = getIntent().getStringExtra(Constants.REMOTE_MSG_MEETING_TYPE);
        if (meetingType != null){
            if (meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.ic_videocam);
            }else if (meetingType.equals("audio")){
                imageMeetingType.setImageResource(R.drawable.ic_call);
            }
        }

        String firstName = getIntent().getStringExtra(Constants.KEY_FIRST_NAME);
        String lastName = getIntent().getStringExtra(Constants.KEY_LAST_NAME);
        String email = getIntent().getStringExtra(Constants.KEY_EMAIL);

        if (firstName != null){
            textFirstChar.setText(firstName.substring(0,1));
        }

        textName.setText(firstName + " " + lastName);
        textEmail.setText(email);

//        Toast.makeText(IncomingInvitationActivity.this,"meetingRoom : " + getIntent()
//                .getStringExtra(Constants.REMOTE_MSG_MEETING_ROOM) , Toast.LENGTH_SHORT).show();

        // Click image chấp nhận kết nối
        imageAcceptInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(IncomingInvitationActivity.this, "Token inviter : " +
//                        getIntent().getStringExtra(Constants.REMOTE_MSG_INVITER_TOKEN), Toast.LENGTH_SHORT).show();

                sendInvitationResponse(Constants.REMOTE_MSG_INVITATION_ACCEPTED,
                        getIntent().getStringExtra(Constants.REMOTE_MSG_INVITER_TOKEN));
            }
        });

        // click ko kết nối
        imageRejectInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInvitationResponse(Constants.REMOTE_MSG_INVITATION_REJECTED,
                        getIntent().getStringExtra(Constants.REMOTE_MSG_INVITER_TOKEN));
            }
        });

    }

    private void init() {
        imageMeetingType        = findViewById(R.id.imageMeetingType);
        textFirstChar           = findViewById(R.id.textFirstChar);
        textName                = findViewById(R.id.textUserName);
        textEmail               = findViewById(R.id.textEmail);
        imageAcceptInvitation   = findViewById(R.id.imageAcceptInvitation);
        imageRejectInvitation   = findViewById(R.id.imageRejectInvitation);

    }

    // gửi trả lời phản hồi
    private void sendInvitationResponse(String type, String receiverToken){
        // receiverToken là token của người gửi yêu cầu kết nối tới
        // token này được nhận qua intent từ service còn service nhận token này thông qua gói tin
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put(Constants.REMOTE_MSG_TYPE, Constants.REMOTE_MSG_INVITATION_RESPONSE); // thông báo cho service biết đây là ng nhận trả lời yêu cầu kết nối
            // type này mục đích thông báo là ng nhận đã nhận/hủy để response lại ng gửi, sẵn sàng kết nối

            data.put(Constants.REMOTE_MSG_INVITATION_RESPONSE, type); // đây là type của response  đánh đấu là accept hay reject

            body.put(Constants.REMOTE_MSG_DATA, data);
            body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);

            sendRemoteMessage(body.toString(), type);

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private void sendRemoteMessage(String remoteMessageBody, String type){
        ApiClient.getClient().create(ApiService.class) // getClient() từ ApiClient class
                .sendRemoteMessage(Constants.getRemoteMessageHeaders(), remoteMessageBody) // method này ở interface ApiService, remoteMeBody là tham số của fun
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.isSuccessful()){
                            if (type.equals(Constants.REMOTE_MSG_INVITATION_ACCEPTED)){
                                Toast.makeText(IncomingInvitationActivity.this, "Accept invitation!", Toast.LENGTH_SHORT).show();
                                try {
                                    URL serverURL = new URL("https://meet.jit.si");

                                    // chung cả audio và video
                                    JitsiMeetConferenceOptions.Builder builder = new JitsiMeetConferenceOptions.Builder();
                                    builder.setServerURL(serverURL);
                                    builder.setWelcomePageEnabled(false);
                                    builder.setRoom(getIntent().getStringExtra(Constants.REMOTE_MSG_MEETING_ROOM));


                                    // của audio
                                    if (meetingType.equals("audio")){
                                        builder.setVideoMuted(true);
                                    }

                                    JitsiMeetActivity.launch(IncomingInvitationActivity.this, builder.build());
                                    finish(); // bắt buộc phải có finish ở đây, nếu ko lỗi sml nhá :D


                                } catch (MalformedURLException e) {
                                    Toast.makeText(IncomingInvitationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }else if (type.equals(Constants.REMOTE_MSG_INVITATION_REJECTED)){
                                Toast.makeText(IncomingInvitationActivity.this, "Reject invitation!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }else{
                            Toast.makeText(IncomingInvitationActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Toast.makeText(IncomingInvitationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }

    private BroadcastReceiver invitationResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra(Constants.REMOTE_MSG_INVITATION_RESPONSE);
            if (type != null){
                if (type.equals(Constants.REMOTE_MSG_INVITATION_CANCELLED)){
                    Toast.makeText(context, "Inviter cancel invitation!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    };

    // Đăng ký broadcast
    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                invitationResponseReceiver,
                new IntentFilter(Constants.REMOTE_MSG_INVITATION_RESPONSE)
        );
    }

    // hủy đăng kí
    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(invitationResponseReceiver);
    }
}