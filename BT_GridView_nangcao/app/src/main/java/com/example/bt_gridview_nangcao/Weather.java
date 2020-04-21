package com.example.bt_gridview_nangcao;

public class Weather {
    private String Ten;
    private int hinhAnh;

    public Weather(String ten, int hinhAnh) {
        Ten = ten;
        this.hinhAnh = hinhAnh;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
