package com.example.android_bai3_layout.model;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private String id, name;
    private ArrayList<Product> products;

    public Catalog(String id, String name, ArrayList<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public Catalog(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
