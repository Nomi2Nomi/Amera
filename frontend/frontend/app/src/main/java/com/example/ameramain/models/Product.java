package com.example.ameramain.models;

public class Product {
    private int id;
    private String name;
    private String imageUrl;
    private double price;
    private boolean isFavourite;

    public Product(int id, String name, String imageUrl, double price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public String getImageUrl() {
        return imageUrl;
    }
    public double getPrice() { return price; }
    public boolean isFavourite() {
        return isFavourite;
    }
    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}

