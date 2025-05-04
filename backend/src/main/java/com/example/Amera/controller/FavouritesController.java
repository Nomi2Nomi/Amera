package com.example.Amera.controller;

import com.example.Amera.entity.Favourites;
import com.example.Amera.entity.Product;
import com.example.Amera.entity.User;
import com.example.Amera.repository.FavouritesRepository;
import com.example.Amera.repository.ProductRepository;
import com.example.Amera.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // Получить избранное пользователя
    @GetMapping("/{userId}")
    public ResponseEntity<List<Favourites>> getUserFavourites(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Favourites> favourites = favouritesRepository.findByUser(user.get());
        return ResponseEntity.ok(favourites);
    }

    // Добавить продукт в избранное
    @PostMapping
    public ResponseEntity<String> addToFavourites(@RequestBody Favourites favourite) {
        Optional<User> user = userRepository.findById(favourite.getUser().getId());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Optional<Product> product = productRepository.findById(favourite.getProduct().getId());
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        Optional<Favourites> existingFavourite = favouritesRepository.findByUserAndProduct(user.get(), product.get());
        if (existingFavourite.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product already in favourites");
        }

        Favourites newFavourite = Favourites.builder()
                .user(user.get())
                .product(product.get())
                .build();

        favouritesRepository.save(newFavourite);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added to favourites");
    }

    // Удалить продукт из избранного
    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<String> removeFromFavourites(@PathVariable Long userId, @PathVariable Long productId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Optional<Product> product = productRepository.findById(productId);
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
}
