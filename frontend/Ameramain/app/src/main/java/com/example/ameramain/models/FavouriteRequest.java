package com.example.ameramain.models;

public class FavouriteRequest {
    private Long userId;
    private Long productId;

    public FavouriteRequest(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }
}
