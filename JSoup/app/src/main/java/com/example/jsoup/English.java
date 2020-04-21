package com.example.jsoup;

public class English {
    private String image;
    private String content;
    private String url;
    public English(String image, String content,String url) {
        this.image = image;
        this.content = content;
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }
}
