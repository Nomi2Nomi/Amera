package com.example.Amera.service;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Amera.entity.Product;
import com.example.Amera.entity.User;
import com.example.Amera.repository.UserRepository;

import java.util.List;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class AppService {
    private List<Product> products;
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;


    
    public List<Product> allProducts(){
    	return products;
    }

    public Product productByID(int id) {
    	return products.stream()
    			.filter(app -> app.getId() == id)
    			.findFirst()
    			.orElse(null);
    }
    

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
}
