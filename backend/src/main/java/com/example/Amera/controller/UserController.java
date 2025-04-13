package com.example.Amera.controller;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Amera.dto.UserDto;
import com.example.Amera.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:8081")  // Укажите адрес вашего фронтенда
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);  // Логгер для логирования
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        logger.info("Received registration request for: {}", userDto);  // Логируем полученные данные пользователя
        try {
            // Пытаемся зарегистрировать пользователя
            userService.register(userDto);
            logger.info("User {} registered successfully", userDto.getEmail());  // Логируем успешную регистрацию
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Пользователь зарегистрирован"));
        } catch (RuntimeException e) {
            logger.error("Error during registration for user {}: {}", userDto.getEmail(), e.getMessage());  // Логируем ошибку
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
