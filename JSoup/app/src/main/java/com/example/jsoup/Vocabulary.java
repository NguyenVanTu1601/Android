package com.example.jsoup;

public class Vocabulary {
    private String tu;
    private String phienAm;
    private String tuLoai;
    private String Giaithich;

    public Vocabulary(String tu, String phienAm, String tuLoai, String giaithich) {
        this.tu = tu;
        this.phienAm = phienAm;
        this.tuLoai = tuLoai;
        Giaithich = giaithich;
    }

    public String getTu() {
        return tu;
    }

    public String getPhienAm() {
        return phienAm;
    }

    public String getTuLoai() {
        return tuLoai;
    }

    public String getGiaithich() {
        return Giaithich;
    }
}
