package com.example.swipevideo_viewpager2;

public class VideoItem {
    private String videoUrl, videoTitle, videoDescription;



    public VideoItem() {
    }

    public VideoItem(String videoUrl, String videoTitle, String videoDescription) {
        this.videoUrl = videoUrl;
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
    }
    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }
}
