package com.example.intent_explicit_truyen_nhan_bundle;

import java.io.Serializable;
import java.security.PrivateKey;

public class Sinhvien implements Serializable {
    private String MaSv;
    private String TenSv;
    private float Mark;

    public Sinhvien(String maSv, String tenSv, float mark) {
        MaSv = maSv;
        TenSv = tenSv;
        Mark = mark;
    }
}
