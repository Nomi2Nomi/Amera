package com.example.Amera.controller;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Amera.entity.Product;
import com.example.Amera.entity.User;
import com.example.Amera.service.AppService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/apps")
@AllArgsConstructor
public class AppController {
    private AppService service;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to the unprotected page";
    }

    @GetMapping("/all-app")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Product> allApplications() {
        return service.allProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Product productByID(@PathVariable int id) {
        return service.productByID(id);
    }

    @PostMapping("/new-user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
    	logger.info("Received registration request for: {}", user);
    	try {
        service.addUser(user);
        logger.info("User {} registered successfully", user.getEmail());  // Логируем успешную регистрацию
        return ResponseEntity.ok().body(Collections.singletonMap("message", "Пользователь зарегистрирован"));
    	}catch (RuntimeException e){
    		logger.error("Error during registration for user {}: {}", user.getEmail(), e.getMessage());  // Логируем ошибку
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
    	}
    }
   
}
