package com.example.ncovi_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ncovi_2020.Fragment.Fragment_Huongdan;
import com.example.ncovi_2020.Fragment.Fragment_Tacgia;
import com.example.ncovi_2020.Fragment.Fragment_Thegioi;
import com.example.ncovi_2020.Fragment.Fragment_VietNam;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("Việt Nam");
        loadFragment(new Fragment_VietNam());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.viet_nam:
                    toolbar.setTitle("Việt Nam");
                    fragment = new Fragment_VietNam();
                    loadFragment(fragment);
                    return true;
                case R.id.the_gioi:
                    toolbar.setTitle("Thế giới");
                    fragment = new Fragment_Thegioi();
                    loadFragment(fragment);
                    return true;
                case R.id.huong_dan:
                    toolbar.setTitle("Hướng dẫn");
                    fragment = new Fragment_Huongdan();
                    loadFragment(fragment);
                    return true;
                case R.id.contact_author:
                    toolbar.setTitle("Liên hệ tác giả");
                    fragment = new Fragment_Tacgia();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
}