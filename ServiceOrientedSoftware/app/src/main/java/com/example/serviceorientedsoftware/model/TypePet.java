package com.example.serviceorientedsoftware.model;

public class TypePet {
    private int check;
    private int image;
    private String name;
    private String type;

    public TypePet(int check, int image, String name, String type) {
        this.check = check;
        this.image = image;
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
