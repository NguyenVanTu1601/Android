package com.example.chips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

/**
 * Chú ý phải sử dụng theme trong chip nếu ko sẽ lỗi tè le
 * https://ahsensaeed.com/android-chips-component-example/
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Chip chip = findViewById(R.id.chipchip);
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Đã click", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview_chip);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Chips> listChips = new ArrayList<>();
        listChips.add(new Chips("#FFFFFF","Tú"));
        listChips.add(new Chips("#FFFFFF","Tùng"));
        listChips.add(new Chips("#FFFFFF","Trung"));
        listChips.add(new Chips("#FFFFFF","Nam"));
        listChips.add(new Chips("#FFFFFF","Việt"));

        ChipAdapter adapter = new ChipAdapter(listChips, this);
        recyclerView.setAdapter(adapter);
    }
}