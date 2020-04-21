package com.example.fragment_listview_listfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

public class Fragment extends ListFragment {
    String[] arrayCity = {"Hà Nội","Hồ Chí Minh","Hải Phòng","Thái Bình","Lào Cai","Quảng Ninh"};
    ArrayAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,arrayCity);
        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Toast.makeText(getActivity(), arrayCity[position] + "", Toast.LENGTH_SHORT).show();
        super.onListItemClick(l, v, position, id);
    }
}
