package com.example.android_b07_bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.android_b07_bottomnavigation.adapter.BottomAdapter;
import com.example.android_b07_bottomnavigation.databinding.ActivityMainBinding;
import com.example.android_b07_bottomnavigation.fragment.CoffeeFragment;
import com.example.android_b07_bottomnavigation.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager(), BottomAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(adapter);

        binding.bottomNavigate.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        binding.viewPager.setCurrentItem(0);
                        break;
                    case R.id.search:
                        binding.viewPager.setCurrentItem(1);
                        break;
                    case R.id.notification:
                        binding.viewPager.setCurrentItem(2);
                        break;
                    case R.id.coffee:
                        binding.viewPager.setCurrentItem(3);
                        break;


                }
                return true;
            }
        });

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        binding.bottomNavigate.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1:
                        binding.bottomNavigate.getMenu().findItem(R.id.search).setChecked(true);
                        break;
                    case 2:
                        binding.bottomNavigate.getMenu().findItem(R.id.notification).setChecked(true);
                        break;
                    case 3:
                        binding.bottomNavigate.getMenu().findItem(R.id.coffee).setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}