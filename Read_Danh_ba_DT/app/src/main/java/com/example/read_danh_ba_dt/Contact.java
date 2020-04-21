package com.example.read_danh_ba_dt;

import android.graphics.Bitmap;

public class Contact implements Comparable<Contact>{
    private String hoTen;
    private String soDT;
    private Bitmap avata;

    public Contact() {
    }

    public Contact(String hoTen, String soDT, Bitmap avata) {
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.avata = avata;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getSoDT() {
        return soDT;
    }

    public Bitmap getAvata() {
        return avata;
    }

    @Override
    public int compareTo(Contact o) {
        return this.hoTen.compareTo(o.hoTen);
    }
}
