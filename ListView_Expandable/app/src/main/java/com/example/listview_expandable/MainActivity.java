package com.example.listview_expandable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Đã có trong danh mục mô tả
 * Có thêm các thuộc tính như set màu có các đường kẻ  và chiều cao đường kẻ
 * + Màu : divider
 * + Cao : dividerHeight
 * Màu cho các group khi chạm vào listSelector
 * Màu cho đường kẻ của group : childDivider
 *
 */
public class MainActivity extends AppCompatActivity {
    ExpandableListView listView;
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Custom_Expandable_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.expandableListView);
        addControl();
        adapter = new Custom_Expandable_Adapter(MainActivity.this,listDataHeader,listDataChild);
        listView.setAdapter(adapter);

        // Bắt sự kiện click group
        click_group();

        // Bắt sự kiện click item
        click_child();

        // Bắt sự kiện đóng group
        close_group();


        // Bắt sự kiện mở group
        open_group();
    }

    private void open_group() {
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(MainActivity.this, "Mở : " + listDataHeader.get(groupPosition), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void close_group() {
        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(MainActivity.this, "Đóng " + listDataHeader.get(groupPosition), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void click_child() {
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(MainActivity.this, listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void click_group() {
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(MainActivity.this, listDataHeader.get(groupPosition), Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private void addControl() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<>();

        listDataHeader.add("Phim tháng 10");
        listDataHeader.add("Phim tháng 11");
        listDataHeader.add("Phim tháng 12");

        ArrayList<String> phimthang10 = new ArrayList<>();
        if(phimthang10.size() == 0){
            phimthang10.add("Hãy nhắm mắt khi anh đến");
            phimthang10.add("Thiếu nữ toàn phong");
            phimthang10.add("Die Now");
            phimthang10.add("Khí Linh");
        }


        ArrayList<String> phimthang11 = new ArrayList<>();
        if(phimthang11.size() == 0){
            phimthang11.add("Dragonball Supper");
            phimthang11.add("The flash");
            phimthang11.add("Đạo mộ bút kí");
            phimthang11.add("Khí Linh 2");
        }


        ArrayList<String> phimthang12 = new ArrayList<>();
        if(phimthang12.size() == 0){
            phimthang12.add("Dragonball Hero");
            phimthang12.add("Trấn hồn nhai");
            phimthang12.add("Đạo mộ bút kí 2");
            phimthang12.add("Mắt biêc");
            phimthang12.add("Địa đạo cá sấu tử thần");
        }

        listDataChild.put(listDataHeader.get(0),phimthang10);
        listDataChild.put(listDataHeader.get(1),phimthang11);
        listDataChild.put(listDataHeader.get(2),phimthang12);
    }
}
