package com.example.shopinglistapp.Model;

public class Item {
    private int id;
    private String name;
    private String qty;

    public Item(int id, String name, String qty) {
        this.id = id;
        this.name = name;
        this.qty = qty;
    }

    public Item(String name, String qty) {
        this.name = name;
        this.qty = qty;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
