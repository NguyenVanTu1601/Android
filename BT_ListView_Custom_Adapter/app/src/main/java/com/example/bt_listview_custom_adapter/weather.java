package com.example.bt_listview_custom_adapter;

public class weather {
    private int Hinhanh;
    private String Diadiem;
    private String Thoitiet;
    private int nhietdo;

    public weather(int hinhanh, String diadiem, String thoitiet, int nhietdo) {
        Hinhanh = hinhanh;
        Diadiem = diadiem;
        Thoitiet = thoitiet;
        this.nhietdo = nhietdo;
    }

    public int getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(int hinhanh) {
        Hinhanh = hinhanh;
    }

    public String getDiadiem() {
        return Diadiem;
    }

    public void setDiadiem(String diadiem) {
        Diadiem = diadiem;
    }

    public String getThoitiet() {
        return Thoitiet;
    }

    public void setThoitiet(String thoitiet) {
        Thoitiet = thoitiet;
    }

    public int getNhietdo() {
        return nhietdo;
    }

    public void setNhietdo(int nhietdo) {
        this.nhietdo = nhietdo;
    }
}
