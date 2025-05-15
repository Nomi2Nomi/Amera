package com.example.Amera.controller;

import com.example.Amera.dto.CartItemResponse;
import com.example.Amera.entity.Cart;
import com.example.Amera.entity.Product;
import com.example.Amera.entity.User;
import com.example.Amera.repository.CartRepository;
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
@RequestMapping("/api/v1/apps/cart")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartController(CartRepository cartRepository,
                                ProductRepository productRepository,
                                UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Получить продукты в избранном
    @Transactional
    @GetMapping("/products/{userId}")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@PathVariable("userId") Long userId) {
        Optional<User> user = getUser(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<CartItemResponse> items = cartRepository.findByUser(user.get()).stream()
                .map(cart -> new CartItemResponse(
                        cart.getProduct(),
                        cart.getQuantity()
                ))
                .toList();

        return ResponseEntity.ok(items);
    }


    // Добавить продукт в избранное (передаем данные в виде Map<String, Long>)
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        Long productId = body.get("productId");

        if (userId == null || productId == null) {
            return ResponseEntity.badRequest().body("Missing userId or productId");
        }

        Optional<User> user = getUser(userId);
        Optional<Product> product = getProduct(productId);
        if (user.isEmpty() || product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product not found");
        }

        Optional<Cart> existingCart = cartRepository.findByUserAndProduct(user.get(), product.get());
        if (existingCart.isPresent()) {
            Cart cartItem = existingCart.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartRepository.save(cartItem);
            return ResponseEntity.ok("Quantity increased");
        }

        Cart newCart = Cart.builder()
                .user(user.get())
                .product(product.get())
                .quantity(1)
                .build();

        cartRepository.save(newCart);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added to cart");
    }

    
    @PutMapping("/updateQuantity")
    public ResponseEntity<String> updateQuantity(@RequestBody Map<String, Object> body) {
        Long userId = ((Number) body.get("userId")).longValue();
        Long productId = ((Number) body.get("productId")).longValue();
        Integer quantity = ((Number) body.get("quantity")).intValue();

        Optional<User> user = getUser(userId);
        Optional<Product> product = getProduct(productId);
        if (user.isEmpty() || product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product not found");
        }

        Optional<Cart> cart = cartRepository.findByUserAndProduct(user.get(), product.get());
        if (cart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found");
        }

        Cart cartItem = cart.get();
        if (quantity <= 0) {
            cartRepository.delete(cartItem);
            return ResponseEntity.ok("Item removed");
        }

        cartItem.setQuantity(quantity);
        cartRepository.save(cartItem);
        return ResponseEntity.ok("Quantity updated");
    }


    // Удалить продукт из избранного (передаем данные в виде Map<String, Long>)
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody Map<String, Long> body) {
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

        Optional<Cart> cart = cartRepository.findByUserAndProduct(user.get(), product.get());
        if (cart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }

        cartRepository.delete(cart.get());
        return ResponseEntity.ok("Product removed from cart");
    }
    
 // Очистить всю корзину пользователя
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable("userId") Long userId) {
        Optional<User> user = getUser(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        List<Cart> userCart = cartRepository.findByUser(user.get());
        if (userCart.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart is already empty");
        }

        cartRepository.deleteAll(userCart);
        return ResponseEntity.ok("Cart cleared");
    }

    // Получить количество товаров в корзине
    @GetMapping("/count/{userId}")
    public ResponseEntity<Integer> countCartItems(@PathVariable("userId") Long userId) {
        Optional<User> user = getUser(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }

        int count = cartRepository.findByUser(user.get()).size();
        return ResponseEntity.ok(count);
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
