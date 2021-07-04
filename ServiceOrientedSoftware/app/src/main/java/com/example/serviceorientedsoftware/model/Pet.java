package com.example.serviceorientedsoftware.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pet implements Serializable {
    @SerializedName("id")
    private String id;

//    @SerializedName("userId")
//    private String idUser;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private double price;

    @SerializedName("type")
    private String type;

    @SerializedName("description")
    private String description;

    @SerializedName("imgUrl")
    private String img_url;

    @SerializedName("age")
    private int age;

    @SerializedName("color")
    private String color;

    @SerializedName("weight")
    private int weight;

    @SerializedName("sex")
    private String sex;

    @SerializedName("species")
    private String species;

//    @SerializedName("state")
//    private int state;

    public Pet() {
    }

//    public Pet(String id, String idUser, String name, double price, String type, String description, String img_url,
//               int age, String color, int weight, String sex, String species, int state) {
//        this.id = id;
//        this.idUser = idUser;
//        this.name = name;
//        this.price = price;
//        this.type = type;
//        this.description = description;
//        this.img_url = img_url;
//        this.age = age;
//        this.color = color;
//        this.weight = weight;
//        this.sex = sex;
//        this.species = species;
//        this.state = state;
//    }

    public Pet(String id, String name, double price, String type,
               String description, String img_url, int age, String color, int weight, String sex, String species) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.description = description;
        this.img_url = img_url;
        this.age = age;
        this.color = color;
        this.weight = weight;
        this.sex = sex;
        this.species = species;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getImg_url() {
        return img_url;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }

    public String getSex() {
        return sex;
    }

    public String getSpecies() {
        return species;
    }

//    public String getIdUser() {
//        return idUser;
//    }
//
//    public int getState() {
//        return state;
//    }
//
//    public void setState(int state) {
//        this.state = state;
//    }
}
