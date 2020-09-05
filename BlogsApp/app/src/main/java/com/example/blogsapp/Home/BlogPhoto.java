package com.example.blogsapp.Home;

public class BlogPhoto extends BlogPhotoId {
    public String uid;
    public String datetime;
    public String description;
    public String image;

    public BlogPhoto() {
    }

    public BlogPhoto(String uid, String datetime, String description, String image) {
        this.uid = uid;
        this.datetime = datetime;
        this.description = description;
        this.image = image;
    }

    public String getUid() {
        return uid;
    }


    public String getDatetime() {
        return datetime;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
