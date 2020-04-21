package com.example.messenger;

public class DataShop {
    private int HinhAnh;
    private String ten;

    public int getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public DataShop(int hinhAnh, String ten) {
        HinhAnh = hinhAnh;
        this.ten = ten;
    }
}
