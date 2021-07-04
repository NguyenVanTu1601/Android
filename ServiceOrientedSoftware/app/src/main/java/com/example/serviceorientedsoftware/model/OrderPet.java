package com.example.serviceorientedsoftware.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderPet implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("orderId")
    private String order_id;

    @SerializedName("productId")
    //@SerializedName("petId") - co v.a
    private String pet_id;

    @SerializedName("productName")
    //@SerializedName("petName") - co v.a
    private String pet_name;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("unitPrice")
    private int unit_price;

    public OrderPet() {
    }

    public OrderPet(String pet_id, String pet_name, int quantity, int unit_price) {
        this.pet_id = pet_id;
        this.pet_name = pet_name;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public OrderPet(String id, String order_id, String pet_id, String pet_name, int quantity, int unit_price) {
        this.id = id;
        this.order_id = order_id;
        this.pet_id = pet_id;
        this.pet_name = pet_name;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public String getId() {
        return id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getPet_id() {
        return pet_id;
    }

    public String getPet_name() {
        return pet_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnit_price() {
        return unit_price;
    }
}
