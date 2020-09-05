package com.example.blogsapp.Comment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Comment {
    public String currentID;
    public String comment;
    public String dateTime;

    public String getCurrentID() {
        return currentID;
    }

    public String getComment() {
        return comment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Comment(String currentID, String comment, String dateTime) {
        this.currentID = currentID;
        this.comment = comment;
        this.dateTime = dateTime;
    }

    public Comment() {
    }
}
