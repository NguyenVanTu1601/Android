package com.example.scratch_card;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.anupkumarpanwar.scratchview.ScratchView;

/**
 * https://github.com/sharish/ScratchView
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScratchView scratchView = findViewById(R.id.scratchView);
        scratchView.setRevealListener(new ScratchView.IRevealListener() {
            @Override
            public void onRevealed(ScratchView scratchView) { // Nếu xóa đủ
                Toast.makeText(MainActivity.this, "Revealed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRevealPercentChangedListener(ScratchView scratchView, float percent) { // % xóa được
                Toast.makeText(MainActivity.this, percent + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
