package com.example.android_b04_exercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.android_b04_exercise.adapter.PitchAdapter;
import com.example.android_b04_exercise.model.Pitch;

import java.util.ArrayList;

public class PitchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PitchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        ArrayList<Pitch> pitches = new ArrayList<>();
        pitches.add(new Pitch(R.drawable.san1,"Sân Cường Quốc","28 Phùng Khoang, Thanh Xuân, Hà Nội"));
        pitches.add(new Pitch(R.drawable.san2,"Sân Cầu Vòi","Số 8 bốt nước hàng Đậu"));
        pitches.add(new Pitch(R.drawable.san3,"Sân Fusal","Số 9 Dịch Vọng, Hà Nội"));
        pitches.add(new Pitch(R.drawable.san4,"Sân Thành Phúc","108 Thanh Xuân, Hà Nội"));
        pitches.add(new Pitch(R.drawable.san5,"Sân Viettel","Bùi Trạch, Thanh Xuân, Hà Nội"));
        pitches.add(new Pitch(R.drawable.san6,"Sân Đông Anh","Đông Anh, Hà Nội"));
        pitches.add(new Pitch(R.drawable.san7,"Sân Bưu Chính","28 Mộ Lao, Hà Đông, Hà Nội"));

        adapter = new PitchAdapter(pitches, this);
        recyclerView.setAdapter(adapter);
    }
}