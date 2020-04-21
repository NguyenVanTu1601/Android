package com.example.read_danh_ba_dt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Mục tiêu project là đọc dữ liệu của danh bạ điện thoại và đổ ra listview
 * Cần xin quyền read,write contact
 */
public class MainActivity extends AppCompatActivity {
    ArrayList<Contact> listContact;
    Contact_adapter adapter;
    ListView listView;
    ContactManager myContactManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listContact);
        listContact = new ArrayList<>();
        myContactManager = new ContactManager(this);
        listContact = (ArrayList<Contact>) myContactManager.getMyListContact();
        Collections.sort(listContact);
        adapter = new Contact_adapter(this, R.layout.row_contact,listContact);
        listView.setAdapter(adapter);
    }
}
