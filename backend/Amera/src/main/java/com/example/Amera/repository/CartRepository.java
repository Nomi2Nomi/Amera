package com.example.Amera.repository;

import com.example.Amera.entity.Cart;
import com.example.Amera.entity.Product;
import com.example.Amera.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    Optional<Cart> findByUserAndProduct(User user, Product product);
    void deleteByUserAndProduct(User user, Product product);
}
