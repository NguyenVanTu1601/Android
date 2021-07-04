package com.example.android_kt2_bai2.model;

import java.io.Serializable;

public class Donation implements Serializable {
    private int id;
    private String name;
    private String city;
    private String date;
    private int money;

    public Donation() {
    }

    public Donation(String name, String city, String date, int money) {
        this.name = name;
        this.city = city;
        this.date = date;
        this.money = money;
    }

    public Donation(int id, String name, String city, String date, int money) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.date = date;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
