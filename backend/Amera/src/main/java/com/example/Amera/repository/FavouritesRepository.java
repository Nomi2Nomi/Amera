package com.example.Amera.repository;

import com.example.Amera.entity.Favourites;
import com.example.Amera.entity.Product;
import com.example.Amera.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
    List<Favourites> findByUser(User user);
    Optional<Favourites> findByUserAndProduct(User user, Product product);
    void deleteByUserAndProduct(User user, Product product);
}
