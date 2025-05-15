package com.example.ameramain.models;

public class CartProduct {
    private Product product;
    private int quantity;
    private String size;

    public CartProduct(Product product, int quantity, String size) {
        this.product = product;
        this.quantity = quantity;
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
