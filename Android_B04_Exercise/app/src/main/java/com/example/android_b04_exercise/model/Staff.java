package com.example.android_b04_exercise.model;

public class Staff {
    private String maNV;
    private String tenNV;
    private int sex;

    public Staff(String maNV, String tenNV, int sex) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.sex = sex;
    }

    public Staff(String maNV, String tenNV) {
        this.maNV = maNV;
        this.tenNV = tenNV;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
