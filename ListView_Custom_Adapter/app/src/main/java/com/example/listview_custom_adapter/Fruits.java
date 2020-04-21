package com.example.listview_custom_adapter;

public class Fruits {
    private String Name;
    private String Mota;
    private int Hinh;
    public Fruits(){};
    public Fruits(String name, String mota, int hinh) {
        Name = name;
        Mota = mota;
        Hinh = hinh;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }
}
