package com.example.Amera.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Amera.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // Проверка существования по email
    Optional<User> findByEmail(String email);
}



