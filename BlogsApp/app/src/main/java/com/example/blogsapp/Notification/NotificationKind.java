package com.example.blogsapp.Notification;

public class NotificationKind {
    public String loaiNotification;

    public <T extends NotificationKind> T withKind(final String loai){
        this.loaiNotification = loai;
        return (T) this;
    }
}
