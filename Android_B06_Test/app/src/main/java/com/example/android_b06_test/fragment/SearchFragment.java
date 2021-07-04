package com.example.android_b06_test.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android_b06_test.MainActivity;
import com.example.android_b06_test.R;
import com.example.android_b06_test.adapter.MusicSearchAdapter;
import com.example.android_b06_test.model.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchFragment extends Fragment {

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ImageView btnSearch;
    private List<Music> musicList;
    private EditText edtSearch;
    private ListView listMusicSearch;
    private List<Music> listItemSearch;
    private MusicSearchAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        init(view);

        btnSearch.setOnClickListener(view1 -> {
            String name = edtSearch.getText().toString();
            Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
            listItemSearch = searchMusic(name, musicList);
            adapter = new MusicSearchAdapter(listItemSearch, getContext(),R.layout.music_item);
            listMusicSearch.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });


        return view;
    }

    private void init(View view) {
        btnSearch = view.findViewById(R.id.btnSearch);
        edtSearch = view.findViewById(R.id.edt_search);
        musicList = ((MainActivity) getActivity()).getMusicList();
        listMusicSearch = view.findViewById(R.id.listSearch);
        listItemSearch = new ArrayList<>();
        adapter = new MusicSearchAdapter(listItemSearch,getContext(), R.layout.music_item);
        listMusicSearch.setAdapter(adapter);
    }

    public List<Music> searchMusic(String name, List<Music> musics){
        List<Music> list = new ArrayList<>();
        String regex = ".*" + name + ".*"; // tìm từ bắt đầu bằng str
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        for(int i = 0; i < musics.size(); i++){
            matcher = pattern.matcher(musics.get(i).getName());
            if(matcher.find()){
                list.add(musics.get(i));
            }
        }
        return list;
    }
}