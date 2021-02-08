package com.example.crawldata.ScorePackage;

public class Score {
    private String ten_mon;
    private int so_tin;
    private double diem_he_10;
    private String diem_he_4;

    public Score(String ten_mon, int so_tin, double diem_he_10, String diem_he_4) {
        this.ten_mon = ten_mon;
        this.so_tin = so_tin;
        this.diem_he_10 = diem_he_10;
        this.diem_he_4 = diem_he_4;
    }

    public Score() {
    }

    public String getTen_mon() {
        return ten_mon;
    }

    public void setTen_mon(String ten_mon) {
        this.ten_mon = ten_mon;
    }

    public int getSo_tin() {
        return so_tin;
    }

    public void setSo_tin(int so_tin) {
        this.so_tin = so_tin;
    }

    public double getDiem_he_10() {
        return diem_he_10;
    }

    public void setDiem_he_10(double diem_he_10) {
        this.diem_he_10 = diem_he_10;
    }

    public String getDiem_he_4() {
        return diem_he_4;
    }

    public void setDiem_he_4(String diem_he_4) {
        this.diem_he_4 = diem_he_4;
    }

    @Override
    public String toString() {
        return this.ten_mon + "\n" + this.so_tin + "\n" + this.diem_he_10 + "\n" + this.diem_he_4 + "\n";
    }
}
