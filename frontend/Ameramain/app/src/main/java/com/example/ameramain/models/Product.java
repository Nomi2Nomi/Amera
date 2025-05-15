package com.example.ameramain.models;

import com.google.gson.annotations.SerializedName;
import kotlin.jvm.Transient;

public class Product {
    private int id;
    private String name;

    @SerializedName("imageurl")
    private String imageUrl;

    private double price;

    // Поле для отслеживания избранного, помеченное как @Transient, чтобы не сериализовывалось
    @Transient
    private boolean isFavourite;

    // Конструктор для инициализации всех полей
    public Product(int id, String name, String imageUrl, double price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    // Конструктор без параметров (для потенциального использования в определенных случаях)
    public Product() {
        // Пустой конструктор для возможных сериализаций или случаев без инициализации
    }

    // Геттеры и Сеттеры
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        this.isFavourite = favourite;
    }
}
