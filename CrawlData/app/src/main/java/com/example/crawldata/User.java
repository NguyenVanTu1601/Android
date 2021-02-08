package com.example.crawldata;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String password;
    private String maSV;
    private String tenSV;
    private String gioitinh;
    private String lop;
    private String nganh;
    private String khoa;
    private String hedaotao;
    private String khoahoc;
    private String covan;

    public User() {
    }

    public User(String userName, String password, String maSV, String tenSV, String gioitinh,
                String lop, String nganh, String khoa, String hedaotao, String khoahoc, String covan) {
        this.userName = userName;
        this.password = password;
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.gioitinh = gioitinh;
        this.lop = lop;
        this.nganh = nganh;
        this.khoa = khoa;
        this.hedaotao = hedaotao;
        this.khoahoc = khoahoc;
        this.covan = covan;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nganh) {
        this.nganh = nganh;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getHedaotao() {
        return hedaotao;
    }

    public void setHedaotao(String hedaotao) {
        this.hedaotao = hedaotao;
    }

    public String getKhoahoc() {
        return khoahoc;
    }

    public void setKhoahoc(String khoahoc) {
        this.khoahoc = khoahoc;
    }

    public String getCovan() {
        return covan;
    }

    public void setCovan(String covan) {
        this.covan = covan;
    }
}
