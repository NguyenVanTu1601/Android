package com.example.firebase_storage_database_image;

public class Image {
    private String tenHinh;
    private String linkHinh;

    public Image() {
    }

    public Image(String tenHinh, String linkHinh) {
        this.tenHinh = tenHinh;
        this.linkHinh = linkHinh;
    }

    public String getTenHinh() {
        return tenHinh;
    }

    public void setTenHinh(String tenHinh) {
        this.tenHinh = tenHinh;
    }

    public String getLinkHinh() {
        return linkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        this.linkHinh = linkHinh;
    }
}
