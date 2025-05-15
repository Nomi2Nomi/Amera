package com.example.Amera.dto;

public class LoginResponse {
    private String token;
    private Long userId;
    private String error;

    // Конструктор для токена
    public LoginResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    // Конструктор для ошибки
    public LoginResponse() {
        // Пустой конструктор для ошибки
    }

    // Геттеры и сеттеры

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    // Метод для установки ошибки
    public void setErrorMessage(String error) {
        this.error = error;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
