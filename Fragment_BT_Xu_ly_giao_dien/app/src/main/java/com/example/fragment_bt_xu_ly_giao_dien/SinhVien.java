package com.example.fragment_bt_xu_ly_giao_dien;

import java.io.Serializable;

public class SinhVien implements Serializable {
    private String hoTen;
    private int namSinh;
    private String diaChi;
    private String email;

    public SinhVien(String hoTen, int namSinh, String diaChi, String email) {

        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.diaChi = diaChi;
        this.email = email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
