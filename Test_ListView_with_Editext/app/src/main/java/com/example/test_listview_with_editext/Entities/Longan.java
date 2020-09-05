package com.example.test_listview_with_editext.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "longan")
public class Longan implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "trongluong")
    private int trongLuong;

    @ColumnInfo(name = "dongia")
    private int donGia;


    public Longan(String date, int trongLuong, int donGia) {
        this.date = date;
        this.trongLuong = trongLuong;
        this.donGia = donGia;
    }

    public Longan() {
    }

    public String getDate() {
        return date;
    }

    public int getTrongLuong() {
        return trongLuong;
    }

    public int getDonGia() {
        return donGia;
    }
}
