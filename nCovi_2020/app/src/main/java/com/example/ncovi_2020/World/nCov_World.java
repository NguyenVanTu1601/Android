package com.example.ncovi_2020.World;

public class nCov_World {
    private String nameCountry;     // tên quốc gia
    private int deaths;            // tử vong
    private int confirmed;         // số ca nhiễm
    private int recovered;         // bình phục


    public nCov_World(String nameCountry,int deaths, int confirmed, int recovered) {
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
