package com.example.firebase;

import java.io.Serializable;

public class Sinhvien implements Serializable {
    public String Hoten;
    public String diaChi;
    public int namSinh;

    // Firebase yêu cầu 1 contructor rỗng nó cần thiết khi mình nhận về

    public Sinhvien() {
    }

    public Sinhvien(String hoten, String diaChi, int namSinh) {
        this.Hoten = hoten;
        this.diaChi = diaChi;
        this.namSinh = namSinh;
    }
}
