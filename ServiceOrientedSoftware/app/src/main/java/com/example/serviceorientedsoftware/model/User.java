package com.example.serviceorientedsoftware.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private String address;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;


    public User() {
    }

    public User(String email, String address, String name, String image) {
        this.email = email;
        this.address = address;
        this.name = name;
        this.image = image;
    }

    public User(String id, String email, String address, String name, String image) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
