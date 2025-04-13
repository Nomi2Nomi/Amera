package com.example.Amera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Amera.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // Проверка существования по email
    User findByEmail(String email); // Поиск пользователя по email
}



