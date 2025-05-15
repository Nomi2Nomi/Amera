package com.example.Amera.controller;

import com.example.Amera.entity.Favourites;
import com.example.Amera.entity.Product;
import com.example.Amera.entity.User;
import com.example.Amera.repository.FavouritesRepository;
import com.example.Amera.repository.ProductRepository;
import com.example.Amera.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/apps/favourites")
@CrossOrigin(origins = "*")
public class FavouritesController {

    private final FavouritesRepository favouritesRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public FavouritesController(FavouritesRepository favouritesRepository,
                                ProductRepository productRepository,
                                UserRepository userRepository) {
        this.favouritesRepository = favouritesRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Получить продукты в избранном
    @Transactional
    @GetMapping("/products/{userId}")
    public ResponseEntity<List<Product>> getFavouriteProducts(@PathVariable("userId") Long userId) {
        Optional<User> user = getUser(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Favourites> favourites = favouritesRepository.findByUser(user.get());
        List<Product> favouriteProducts = favourites.stream()
                .map(Favourites::getProduct)
                .toList();

        // Помечаем, что это избранное (для Android)
        favouriteProducts.forEach(p -> p.setFavourite(true));

        return ResponseEntity.ok(favouriteProducts);
    }

    // Добавить продукт в избранное (передаем данные в виде Map<String, Long>)
    @PostMapping("/add")
    public ResponseEntity<String> addToFavourites(@RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        Long productId = body.get("productId");

        Optional<User> user = getUser(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Optional<Product> product = getProduct(productId);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        Optional<Favourites> existingFavourite = favouritesRepository.findByUserAndProduct(user.get(), product.get());
        if (existingFavourite.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already in favourites");
        }

        Favourites newFavourite = Favourites.builder()
                .user(user.get())
                .product(product.get())
                .build();

        favouritesRepository.save(newFavourite);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added to favourites");
    }

    // Удалить продукт из избранного (передаем данные в виде Map<String, Long>)
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromFavourites(@RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        Long productId = body.get("productId");

        Optional<User> user = getUser(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Optional<Product> product = getProduct(productId);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        Optional<Favourites> favourite = favouritesRepository.findByUserAndProduct(user.get(), product.get());
        if (favourite.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favourite not found");
        }

        favouritesRepository.delete(favourite.get());
        return ResponseEntity.ok("Product removed from favourites");
    }

    // Вспомогательный метод для получения пользователя
    private Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    // Вспомогательный метод для получения продукта
    private Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }
}
