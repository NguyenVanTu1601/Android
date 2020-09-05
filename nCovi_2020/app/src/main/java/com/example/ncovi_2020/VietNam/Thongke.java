package com.example.ncovi_2020.VietNam;

public class Thongke {
    private String ten;
    private int soCaNhiem;
    private int dangDieuTri;
    private int Khoi;
    private int tuVong;

    public Thongke() {
    }

    public Thongke(String ten, int soCaNhiem, int dangDieuTri, int khoi, int tuVong) {
        this.ten = ten;
        this.soCaNhiem = soCaNhiem;
        this.dangDieuTri = dangDieuTri;
        Khoi = khoi;
        this.tuVong = tuVong;
    }

    public String getTen() {
        return ten;
    }

    public int getSoCaNhiem() {
        return soCaNhiem;
    }

    public int getDangDieuTri() {
        return dangDieuTri;
    }

    public int getKhoi() {
        return Khoi;
    }

    public int getTuVong() {
        return tuVong;
    }
}
