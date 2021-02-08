package com.example.recyclerviewdeletemulti;

public class Info {
    private int id;
    private String name;

    public Info(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Info() {
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
}
