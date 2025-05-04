package com.example.ameramain.models;

public class RegisterRequest {
    public String name;
    public String surname;
    public String phone;
    public String email;
    public String password;

    public RegisterRequest(String name, String surname, String phone, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }
}
