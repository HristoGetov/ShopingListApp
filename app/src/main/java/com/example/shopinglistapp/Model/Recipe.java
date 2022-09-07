package com.example.shopinglistapp.Model;

import java.util.ArrayList;

public class Recipe {

    private int id;
    private String recipeName;
    private ArrayList<Item> items;
    private Integer prepTime;
    private Integer cookTime;

    public Recipe() {
    }

    public Recipe(int id, String recipeName, ArrayList<Item> items, Integer prepTime, Integer cookTime) {
        this.id = id;
        this.recipeName = recipeName;
        this.items = items;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
    }

    public Recipe(String recipeName, ArrayList<Item> items, Integer prepTime, Integer cookTime) {
        this.recipeName = recipeName;
        this.items = items;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }
}
