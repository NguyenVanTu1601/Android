package com.example.app_like_amazon.Model;

public class Cart {
    private String pid, pname,price, quanlity, discount;
    private String date, time;

    public Cart() {
    }

    public Cart(String pid, String pname, String price, String quanlity, String discount, String date, String time) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quanlity = quanlity;
        this.discount = discount;
        this.date = date;
        this.time = time;
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public String getPrice() {
        return price;
    }

    public String getQuanlity() {
        return quanlity;
    }

    public String getDiscount() {
        return discount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
