package com.example.ncov_api;

public class nCov {
    private String nameCountry;     // tên quốc gia
    private int deaths;            // tử vong
    private int confirmed;         // số ca nhiễm
    private int recovered;         // bình phục


    public nCov(String nameCountry,int deaths, int confirmed, int recovered) {
        this.nameCountry = nameCountry;
        this.deaths = deaths;
        this.confirmed = confirmed;
        this.recovered = recovered;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getRecovered() {
        return recovered;
    }
}
