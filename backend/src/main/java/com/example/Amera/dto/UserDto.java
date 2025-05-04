package com.example.Amera.dto;

public class UserDto {
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private String roles;

    // Геттеры и сеттеры

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getRoles(String roles) {
    	return roles;
    }
    public void setRoles(String roles) {
    	this.roles=roles;
    }
}



