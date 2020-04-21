package com.example.huong_doi_tuong;

public class Sinhvien {
    private String Hoten;
    private String Address;
    private int Year;

    public Sinhvien(){};
    public Sinhvien(String hoten, String address, int year) {
        Hoten = hoten;
        Address = address;
        Year = year;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }


}
