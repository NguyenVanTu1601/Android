package com.example.blogsapp.Home;


public class BlogPhotoId {
    public String blogId;

    public <T extends BlogPhotoId> T withId(final String blogId){
        this.blogId = blogId;
        return (T) this;
    }
}
