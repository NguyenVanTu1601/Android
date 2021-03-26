package com.example.quizzgame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quizzgame.adapter.CategoryAdapter;
import com.example.quizzgame.databinding.ActivityMainBinding;
import com.example.quizzgame.fragment.HomeFragment;
import com.example.quizzgame.fragment.LeaderBoardFragment;
import com.example.quizzgame.fragment.ProfileFragment;
import com.example.quizzgame.fragment.WalletFragment;
import com.example.quizzgame.model.CategoryModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolBar);

        binding.toolBar.setTitle("Home");
        loadFragment(new HomeFragment());

        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                Fragment fragment;
                switch (i){
                    case 0 :
                        binding.toolBar.setTitle("Home");
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case 1:
                        binding.toolBar.setTitle("LeaderBoard");
                        fragment = new LeaderBoardFragment();
                        loadFragment(fragment);
                        return true;
                    case 2:
                        binding.toolBar.setTitle("Wallet");
                        fragment = new WalletFragment();
                        loadFragment(fragment);
                        return true;
                    case 3:
                        binding.toolBar.setTitle("Profile");
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.wallet){
            Toast.makeText(this, "wallet is clicked", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.logout){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}