package com.example.blogsapp.Notification;

public class Notification extends NotificationKind {
    public String id;
    public String idBlog;
    public String time;

    public Notification(String id, String idBlog, String datetime) {
        this.id = id;
        this.idBlog = idBlog;
        this.time = datetime;
    }

    public Notification() {
    }

    public String getId() {
        return id;
    }

    public String getIdBlog() {
        return idBlog;
    }

    public String getTime() {
        return time;
    }
}
