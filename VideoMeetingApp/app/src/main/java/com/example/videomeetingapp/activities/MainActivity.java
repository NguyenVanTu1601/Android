package com.example.videomeetingapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videomeetingapp.R;
import com.example.videomeetingapp.adapter.UserAdapter;
import com.example.videomeetingapp.listeners.UsersListener;
import com.example.videomeetingapp.models.User;
import com.example.videomeetingapp.utilities.Constants;
import com.example.videomeetingapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UsersListener {

    private PreferenceManager preferenceManager;
    private TextView textTitle, textSignOut;
    private RecyclerView userRecyclerView;
    private List<User> users;
    private UserAdapter userAdapter;
    private TextView textErrorMessage;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int REQUEST_CODE_BATTERY_OPYIMIZATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        textTitle.setText(String.format("%s %s", preferenceManager.getString(Constants.KEY_FIRST_NAME),
                preferenceManager.getString(Constants.KEY_LAST_NAME)));


        // get token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null){
                        sendFCMTokenToDatabase(task.getResult().getToken());
                    }
                });

        textSignOut.setOnClickListener(v -> signOut());

        swipeRefreshLayout.setOnRefreshListener(this::getUsers);

        // get danh sách user
        getUsers();

        // check BatteryOptimization()
        checkForBatteryOptimization();
    }

    private void init() {
        preferenceManager   = new PreferenceManager(getApplicationContext());
        textTitle           = findViewById(R.id.textTitle);
        textSignOut         = findViewById(R.id.textSignOut);
        userRecyclerView    = findViewById(R.id.userRecyclerView);
        users = new ArrayList<>();
        userAdapter = new UserAdapter(users, this); // this is userListener
        userRecyclerView.setAdapter(userAdapter);
        textErrorMessage    = findViewById(R.id.textErrorMessage);
        swipeRefreshLayout  = findViewById(R.id.swipeRefreshLayout);
    }

    private void getUsers(){
        swipeRefreshLayout.setRefreshing(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USER)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        swipeRefreshLayout.setRefreshing(false);

                        String myUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                        if (task.isSuccessful() && task.getResult() != null){
                            users.clear();
                            textErrorMessage.setVisibility(View.INVISIBLE);
                            for (QueryDocumentSnapshot documentSnapshots : task.getResult()){
                                // Hiển thị danh sách các người dùng, trừ bản thân mình
                                if (myUserId.equals(documentSnapshots.getId())){
                                    continue;
                                }
                                User user = new User();
                                user.firstName = documentSnapshots.getString(Constants.KEY_FIRST_NAME);
                                user.lastName = documentSnapshots.getString(Constants.KEY_LAST_NAME);
                                user.email = documentSnapshots.getString(Constants.KEY_EMAIL);
                                user.token = documentSnapshots.getString(Constants.KEY_FCM_TOKEN);
                                users.add(user);
                            }
                            if (users.size() > 0){
                                textErrorMessage.setVisibility(View.INVISIBLE);
                                userAdapter.notifyDataSetChanged();
                            }else{
                                textErrorMessage.setText("No users available");
                                textErrorMessage.setVisibility(View.VISIBLE);
                            }
                        }else{
                            textErrorMessage.setText("No users available");
                            textErrorMessage.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
    private void sendFCMTokenToDatabase(String token){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USER)
                .document(preferenceManager.getString(Constants.KEY_USER_ID));
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Token update!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Update token error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signOut(){
        Toast.makeText(this, "Sign Out...", Toast.LENGTH_SHORT).show();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USER)
                .document(preferenceManager.getString(Constants.KEY_USER_ID));
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        preferenceManager.clearPreference();
                        startActivity(new Intent(MainActivity.this, SignInActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public void initiateVideoMeeting(User user) {
        // nếu ng dùng kia ko onl thì cái token của họ đã bị xóa -> ktra token để biết họ onl hay ko
        if (user.token == null || user.token.trim().isEmpty()){
            Toast.makeText(this, user.firstName + " " + user.lastName + " is not available for meeting...", Toast.LENGTH_SHORT).show();
        }else{

            Intent intent = new Intent(getApplicationContext(), OutgoingInvitationActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("type","video");
            startActivity(intent);
        }
    }

    @Override
    public void initiateAudioMeeting(User user) {
        if (user.token == null || user.token.trim().isEmpty()){
            Toast.makeText(this, user.firstName + " " + user.lastName + " is not available for meeting...", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(getApplicationContext(), OutgoingInvitationActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("type","audio");
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void checkForBatteryOptimization(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            if (!powerManager.isIgnoringBatteryOptimizations(getPackageName())){

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Warning!");
                builder.setMessage("Battery optimization is enable. It can interrup running background service");
                builder.setPositiveButton("Disable", ((dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                    startActivityForResult(intent,REQUEST_CODE_BATTERY_OPYIMIZATION);

                }));
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.create().show();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BATTERY_OPYIMIZATION){
            checkForBatteryOptimization();
        }
    }
}