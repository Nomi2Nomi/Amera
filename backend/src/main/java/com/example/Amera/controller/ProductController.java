package com.example.Amera.controller;

import com.example.Amera.entity.Product;
import com.example.Amera.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/apps/products")
@CrossOrigin(origins = "*") // Разрешаем запросы с Android
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
