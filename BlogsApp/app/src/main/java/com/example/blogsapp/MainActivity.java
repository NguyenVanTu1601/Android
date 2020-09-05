package com.example.blogsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.blogsapp.Home.APIService;
import com.example.blogsapp.Home.HomeFragment;
import com.example.blogsapp.Home.NewPostActivity;
import com.example.blogsapp.MyPhoto.AccountFragment;
import com.example.blogsapp.Notification.NotificationFragment;
import com.example.blogsapp.PushNotification.Client;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;

    private Toolbar mainToolBar;
    private ImageView imageAddPost;
    private CircleImageView profileHome;
    private TextView textHome;
    private BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        readInforUser();

        loadFragment(new HomeFragment());

        navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        imageAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewPostActivity.class));
            }
        });

        textHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewPostActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {

        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initialization() {

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();

        mainToolBar = findViewById(R.id.ToolBar);
        setSupportActionBar(mainToolBar);
        getSupportActionBar().setTitle("Photo Blog");

        imageAddPost = findViewById(R.id.image_share_home);
        profileHome = findViewById(R.id.profile_home);
        textHome = findViewById(R.id.text_home);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_btn:
                break;
            case R.id.action_account_btn:
                startActivity(new Intent(MainActivity.this, AccountSettingActivity.class));
                break;
            case R.id.action_logout_btn:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.action_author_btn:
                startActivity(new Intent(MainActivity.this, AuthorActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void readInforUser() {
        rootRef.child("Users").child(currentUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")) && (dataSnapshot.hasChild("image"))) {

                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveUserPhone = dataSnapshot.child("phone").getValue().toString();
                            String retrieveUserAddress = dataSnapshot.child("address").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();

                            Glide.with(MainActivity.this)
                                    .load(retrieveProfileImage)
                                    .error(R.drawable.profile_image)
                                    .into(profileHome);

                        } else {
                            profileHome.setImageResource(R.drawable.profile_image);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    mainToolBar.setTitle("Home");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.home_myphoto:
                    mainToolBar.setTitle("My photo");
                    fragment = new AccountFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.home_notification:
                    mainToolBar.setTitle("Notification");
                    fragment = new NotificationFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
}