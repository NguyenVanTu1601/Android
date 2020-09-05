package com.example.swipevideo_viewpager2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 videoViewPager2 =  findViewById(R.id.videoViewPager);
        List<VideoItem> videoItems = new ArrayList<>();

        VideoItem videoItemCelebration = new VideoItem();
        videoItemCelebration.setVideoUrl("https://www.infinityandroid.com/videos/video1.mp4");
        videoItemCelebration.setVideoTitle("Celebration");
        videoItemCelebration.setVideoDescription("Celebrate who you are in your deepest heart");
        videoItems.add(videoItemCelebration);


        VideoItem videoItemParty = new VideoItem();
        videoItemParty.setVideoUrl("https://www.infinityandroid.com/videos/video2.mp4");
        videoItemParty.setVideoTitle("Party");
        videoItemParty.setVideoDescription("This is party my life");
        videoItems.add(videoItemParty);

        VideoItem videoItemExercise = new VideoItem();
        videoItemExercise.setVideoUrl("https://www.infinityandroid.com/videos/video3.mp4");
        videoItemExercise.setVideoTitle("Exercise");
        videoItemExercise.setVideoDescription("I like down until it gost away");
        videoItems.add(videoItemExercise);

        VideoItem videoItemNature = new VideoItem();
        videoItemNature.setVideoUrl("https://www.infinityandroid.com/videos/video4.mp4");
        videoItemNature.setVideoTitle("Nature");
        videoItemNature.setVideoDescription("I love it very much");
        videoItems.add(videoItemNature);

        VideoItem videoItemTravel = new VideoItem();
        videoItemTravel.setVideoUrl("https://www.infinityandroid.com/videos/video5.mp4");
        videoItemTravel.setVideoTitle("Travel");
        videoItemTravel.setVideoDescription("It is a travel in my life");
        videoItems.add(videoItemTravel);

        VideoItem videoItemChill = new VideoItem();
        videoItemChill.setVideoUrl("https://www.infinityandroid.com/videos/video6.mp4");
        videoItemChill.setVideoTitle("Chill");
        videoItemChill.setVideoDescription("My chill of me");
        videoItems.add(videoItemChill);

        videoViewPager2.setAdapter(new VideoAdapter(videoItems));
    }
}