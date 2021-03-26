package com.example.android_bai3_layout.model;

public class Fruit {
    private String vietnamese;
    private String english;
    private int image;

    public Fruit( String english, String vietnamese, int image) {
        this.vietnamese = vietnamese;
        this.english = english;
        this.image = image;
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
