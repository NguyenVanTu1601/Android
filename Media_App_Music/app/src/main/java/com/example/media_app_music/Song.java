package com.example.media_app_music;

public class Song {
    private String tenBaiHat;
    private int File;

    public Song(String tenBaiHat, int file) {
        this.tenBaiHat = tenBaiHat;
        File = file;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public int getFile() {
        return File;
    }
}
