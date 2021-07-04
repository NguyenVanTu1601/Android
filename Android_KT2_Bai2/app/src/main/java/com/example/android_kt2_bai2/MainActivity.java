package com.example.android_kt2_bai2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android_kt2_bai2.activity.AddDonateActivity;
import com.example.android_kt2_bai2.adapter.DonationAdapter;
import com.example.android_kt2_bai2.database.Database;
import com.example.android_kt2_bai2.databinding.ActivityMainBinding;
import com.example.android_kt2_bai2.model.Donation;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DonationAdapter adapter;
    private List<Donation> donations;
    private Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = new Database(this);

        donations = database.getListAll();
        adapter = new DonationAdapter(donations,this);
        binding.recyclerVew.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerVew.setHasFixedSize(true);
        binding.recyclerVew.setAdapter(adapter);

        binding.FabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddDonateActivity.class));
            }
        });

        if (donations != null && donations.size() > 0 ){
            binding.totalMoney.setVisibility(View.VISIBLE);
            long money = 0;
            for (Donation donation : donations){
                money += donation.getMoney();
            }
            binding.totalMoney.setText("Tổng tiền quyên góp : " + money);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Donation> list = database.getDonationByName(newText);
                adapter.setDonation(list);
                binding.recyclerVew.setAdapter(adapter);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}