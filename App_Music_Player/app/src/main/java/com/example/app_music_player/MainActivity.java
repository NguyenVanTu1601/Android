package com.example.app_music_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> itemsAll;
    private ListView mSongsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSongsList = findViewById(R.id.songsList);
        appExternalStoragePemission();


    }

    private void appExternalStoragePemission(){ // check permission read storage

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        displayAudioSongsName();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {

                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    public ArrayList<File> readOnlyAudioSongs(File file){
        ArrayList<File> arrayList = new ArrayList<>();

        File[] allFile = file.listFiles();
        for(File individuaFile : allFile){
            if(individuaFile.isDirectory() && individuaFile.isHidden()){
                arrayList.addAll(readOnlyAudioSongs(individuaFile));
            }else{
                if(individuaFile.getName().endsWith(".mp3") || individuaFile.getName().endsWith(".aac")
                        ||individuaFile.getName().endsWith(".wav") || individuaFile.getName().endsWith(".wma")
                        || individuaFile.getName().endsWith(".3gpp")){

                    arrayList.add(individuaFile);

                }
            }


        }
        return arrayList;
    }


    private void displayAudioSongsName(){
        // Mở thư mục có tên music và lấy bài hát trong đó
        final ArrayList<File> audioSongs = readOnlyAudioSongs(Environment.getExternalStoragePublicDirectory("Music"));
        // ban đầu là Environment.getExternalStorageDirectory() để đọc toàn bộ nhưng nó ko hoạt động nên thay = đoạnt trên
        itemsAll = new ArrayList<>();
        for(int songCounter = 0; songCounter < audioSongs.size(); songCounter++){
            itemsAll.add(audioSongs.get(songCounter).getName());
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,itemsAll);
        mSongsList.setAdapter(arrayAdapter);

        mSongsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName = mSongsList.getItemAtPosition(position).toString();
                Intent intent = new Intent(MainActivity.this,SmartPlayerActivity.class);
                intent.putExtra("name", songName);
                intent.putExtra("position", position);
                intent.putExtra("song", audioSongs);

                startActivity(intent);
            }
        });
    }
}
