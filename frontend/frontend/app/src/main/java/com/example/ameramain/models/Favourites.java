package com.example.ameramain.models;

public class Favourites {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private String productPrice;
    private String imageUrl; // или base64, если хотите передавать картинки напрямую

    // геттеры/сеттеры
    // Геттеры
    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
