package com.example.fragment_communicator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Là tương tác giữa các fragment
 */
public class MainActivity extends AppCompatActivity {
    TextView txtMain;
    Button btnMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMain = findViewById(R.id.textViewMain);
        btnMain = findViewById(R.id.button2);
        // Tương tác giữa MainActivity với fragment
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentA fragmentA = (FragmentA) getSupportFragmentManager().findFragmentById(R.id.fragmentA);
                FragmentB fragmentB = (FragmentB) getSupportFragmentManager().findFragmentById(R.id.fragmentB);
                // Lưu ý khi gán fragment bằng code thì gọi findFragmentBtTag()
                fragmentA.txtFragA.setText("Thay đổi bởi main");
                fragmentB.setContent();
            }
        });

    }
}
