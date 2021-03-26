package com.example.wallpaperapplication;

import java.io.Serializable;

public class DataImage implements Serializable {
    private int thumbnail;
    private int image;
    private String description;

    public DataImage(int thumbnail, int image, String description) {
        this.thumbnail = thumbnail;
        this.image = image;
        this.description = description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
