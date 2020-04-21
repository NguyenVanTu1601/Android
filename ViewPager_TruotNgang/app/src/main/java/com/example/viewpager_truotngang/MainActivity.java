package com.example.viewpager_truotngang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Chả biết sao toàn sai. KO thể gán hình với màu nhuwnng dán chữ thì oke
 * ViewPager thường có thêm circle để tạo mấy cái dấu chấm ở dưới để chuyển trang
 * Để sử dụng circle thì thêm thư viện  implementation 'me.relex:circleindicator:1.3.2'
 * Sau đó tạo đối tượng circle trong activity.xml
 * Nói chung bỏ cir đi thì chạy, thêm vào lỗi. Project này hỏng
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<Face> listFace;
    private ViewPager viewPager;
    smile_adapter adapter ;
    CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleIndicator = findViewById(R.id.circleIndicator);
        viewPager = findViewById(R.id.viewPager);
        initData();
        adapter = new smile_adapter(listFace,this);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
    }
    private void initData(){
        listFace = new ArrayList<>();
        listFace.add(new Face("#ff0",R.drawable.images,"Smile"));
        listFace.add(new Face("#FFC107",R.drawable.images_1,"Smile_1"));
        listFace.add(new Face("#009688",R.drawable.images_2,"Smile_2"));
        listFace.add(new Face("#ff5722",R.drawable.images_3,"Smile_3"));
        listFace.add(new Face("#2196f3",R.drawable.images_4,"Smile_4"));
    }
}
