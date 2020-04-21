package com.example.messenger_socketio;

import java.io.Serializable;

public class User implements Serializable {
    private byte[] avata;
    private String userName;
    private String passWord;
    private String tenNguoiDung;
    private String Phonenumber;

    public User(byte[] avata, String userName, String passWord, String tenNguoiDung, String phonenumber) {
        this.avata = avata;
        this.userName = userName;
        this.passWord = passWord;
        this.tenNguoiDung = tenNguoiDung;
        Phonenumber = phonenumber;
    }

    public User(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public byte[] getAvata() {
        return avata;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }
}
