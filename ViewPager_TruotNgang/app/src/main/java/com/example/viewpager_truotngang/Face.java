package com.example.viewpager_truotngang;

public class Face {
    private String mColor;
    private int faceId;
    private String name;

    public Face() {
    }

    public Face(String mColor, int faceId, String name) {
        this.mColor = mColor;
        this.faceId = faceId;
        this.name = name;
    }

    public String getmColor() {
        return mColor;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
    }

    public int getFaceId() {
        return faceId;
    }

    public void setFaceId(int faceId) {
        this.faceId = faceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
