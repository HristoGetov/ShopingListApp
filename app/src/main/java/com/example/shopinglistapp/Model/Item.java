package com.example.shopinglistapp.Model;

public class Item {
    private int id;
    private String name;
    private String qty;
    private Type type;

    public Item(int id, String name, String qty,Type type) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.type = type;
    }

    public Item(String name, String qty,Type type) {
        this.name = name;
        this.qty = qty;
        this.type=type;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
