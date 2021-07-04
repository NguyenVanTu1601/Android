package com.example.serviceorientedsoftware.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    @SerializedName("id")
    //@SerializedName("id_order") - co v.a
    private String id;

    @SerializedName("createAt")
    private String create_at;

    @SerializedName("status")
    private String status;

    @SerializedName("totalPrice")
    private int total_price;

    @SerializedName("userId")
    private String user_id;

    @SerializedName("orderProducts")
    //@SerializedName("listOrder") - co v.a
    private List<OrderPet> list;

    public Order() {
    }

    public Order(int total_price, String user_id, List<OrderPet> list) {
        this.total_price = total_price;
        this.user_id = user_id;
        this.list = list;
    }

    public Order(String id, String create_at, String status, int total_price, String user_id, List<OrderPet> list) {
        this.id = id;
        this.create_at = create_at;
        this.status = status;
        this.total_price = total_price;
        this.user_id = user_id;
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public String getCreate_at() {
        return create_at;
    }

    public String getStatus() {
        return status;
    }

    public int getTotal_price() {
        return total_price;
    }

    public String getUser_id() {
        return user_id;
    }

    public List<OrderPet> getList() {
        return list;
    }
}
