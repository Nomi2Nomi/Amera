package com.example.ameramain.models;

public class LoginResponse {
    private String token;
    private Long userId; // Добавляем поле userId
    private String error;

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getError() {
        return error;
    }
}
