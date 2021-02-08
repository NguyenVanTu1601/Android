package com.example.data_binding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.data_binding.databinding.ActivityMainBinding;

/**
 * Là một dạng như SQLite nhưng ngại chưa muốn học :))
 * À không như SQLite đâu, nó hay hơn nhiều, sẽ học trong 1 vài ngày tới khi tìm được nguồn phù hợp
 */
public class MainActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main); // ko cần do khi gọi binding nó sẽ tự tạo
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main); // tham số gồm : activity, layout

        MainViewModel viewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.setUser(user);
            }
        });

        //binding.setUser(user);

        // test data binding thì ko có class MainViewModel và chỉ cần dòng 25, 36 thôi
    }
}
