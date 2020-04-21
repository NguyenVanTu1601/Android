package com.example.sqlite_database;

public class CongViec {
    private int Id;
    private String tenCv;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenCv() {
        return tenCv;
    }

    public void setTenCv(String tenCv) {
        this.tenCv = tenCv;
    }

    public CongViec(int id, String tenCv) {

        Id = id;
        this.tenCv = tenCv;
    }
}
