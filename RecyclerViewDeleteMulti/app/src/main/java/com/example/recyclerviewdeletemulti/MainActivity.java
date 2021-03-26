package com.example.recyclerviewdeletemulti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
// Long click vào tên sẽ hiện thông tin để delete
public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {

    Boolean is_menu_delete = false; // kiểm tra xem có mở menu delete ko để cho phép xóa
    TextView count_select;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    Info_Adapter adapter;
    ArrayList<Info> selection = new ArrayList<>(); // danh sách các item đã chọn để xóa
    ArrayList<Info> listInfo = new ArrayList<>(); // danh sách các item
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        count_select = findViewById(R.id.counter_text);
        count_select.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        listInfo.add(new Info(1,"Nguyễn Văn Tú"));
        listInfo.add(new Info(2,"Nguyễn Văn Tài"));
        listInfo.add(new Info(3,"Phùng Đình Tùng"));
        listInfo.add(new Info(4,"Trịnh Đình Trung"));
        listInfo.add(new Info(5,"Lương Văn Thanh"));
        listInfo.add(new Info(6,"Hoàng Đức An"));


        adapter = new Info_Adapter(listInfo,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onLongClick(View v) {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_delete);
        count_select.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
        is_menu_delete = true;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    public void prepareSelected(View view, int position){
        if (((CheckBox) view).isChecked()){
            selection.add(listInfo.get(position));
            count++;
            updateCounter(count);
        }else {
            selection.remove(listInfo.get(position));
            count--;
            updateCounter(count);
        }
        Toast.makeText(MainActivity.this, "clicked " + position, Toast.LENGTH_SHORT).show();
    }

    public void updateCounter(int counter){
        if (counter == 0){
            count_select.setText("0 item selected");
        }else{
            count_select.setText(counter + " item selected");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete){

            Info_Adapter info_adapter = (Info_Adapter) adapter;
            info_adapter.updateAdapter(selection);
            info_adapter.notifyDataSetChanged();
            clearAction();

        }else if (item.getItemId() == android.R.id.home){ // hành động ấn nút mũi tên back trên actionbar
            clearAction();
            adapter.notifyDataSetChanged();
        }
        return true;
    }

    public void clearAction(){
        is_menu_delete = false; // thoát toolbar khỏi dạng delete
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        count_select.setVisibility(View.GONE);
        count_select.setText("0 item selected");
        count = 0;
        selection.clear();

    }

    @Override
    public void onBackPressed() {
        if (is_menu_delete){
            clearAction();
            adapter.notifyDataSetChanged();
        }else{
            super.onBackPressed();
        }

    }
}