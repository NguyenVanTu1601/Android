package com.example.youtube_api_list;

public class VideoYouTube {
    private String title;
    private String Thumbnail;
    private String idVideo;

    public VideoYouTube(String title, String thumbnail, String idVideo) {
        this.title = title;
        Thumbnail = thumbnail;
        this.idVideo = idVideo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }
}
