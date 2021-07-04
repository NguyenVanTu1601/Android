package com.example.android_b06_test.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android_b06_test.MainActivity;
import com.example.android_b06_test.R;
import com.example.android_b06_test.adapter.MusicAdapter;
import com.example.android_b06_test.model.Music;

import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment {

    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private ListView listView;
    private MusicAdapter adapter;
    private List<Music> musicList;
    private String[] spin_img;
    private EditText edtName, edtDescription;
    private Spinner spinner;
    private Button btnAdd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        init(view);



        btnAdd.setOnClickListener(view1 -> {
            int positon = spinner.getSelectedItemPosition();
            String name = edtName.getText().toString();
            String des = edtDescription.getText().toString();
            Music music = new Music(positon + 1, name, des);

            musicList.add(music);
            adapter.notifyDataSetChanged();
            ((MainActivity)getActivity()).setMusicList(musicList);
        });

        return view;
    }

    private void init(View view) {
        listView = view.findViewById(R.id.listView);
        musicList = new ArrayList<>();
        musicList = ((MainActivity)getActivity()).getMusicList();

        adapter = new MusicAdapter(musicList,getContext(),R.layout.music_item);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        edtName = view.findViewById(R.id.edt_name);
        edtDescription = view.findViewById(R.id.edt_des);
        btnAdd = view.findViewById(R.id.btnAdd);

        spinner = view.findViewById(R.id.spinner);

        spin_img = new String[]{"1","2","3","4","5"};
        ArrayAdapter adapter_spin = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, spin_img);
        adapter_spin.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_spin);


    }
}