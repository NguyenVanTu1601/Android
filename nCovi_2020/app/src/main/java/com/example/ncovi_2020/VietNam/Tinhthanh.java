package com.example.ncovi_2020.VietNam;

public class Tinhthanh {
    private String tenThanhPho;
    private int soCaNhiem;
    private int dangDieuTri;
    private int Khoi;
    private int tuVong;

    public Tinhthanh(String tenThanhPho, int soCaNhiem, int dangDieuTri, int khoi, int tuVong) {
        this.tenThanhPho = tenThanhPho;
        this.soCaNhiem = soCaNhiem;
        this.dangDieuTri = dangDieuTri;
        Khoi = khoi;
        this.tuVong = tuVong;
    }

    public String getTenThanhPho() {
        return tenThanhPho;
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
