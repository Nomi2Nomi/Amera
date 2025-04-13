package com.example.Amera.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")  // Заменили "user" на "users"
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Используем стратегию Identity для автоинкремента
    @Column(name = "user_id") // Указываем имя столбца, если оно отличается от имени поля
    private Long id;
    
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;

    // Геттеры и сеттеры

    public Long getId() {
    	return id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}


