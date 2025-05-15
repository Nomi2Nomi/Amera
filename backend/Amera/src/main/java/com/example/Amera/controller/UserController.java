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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;
import com.example.Amera.entity.User;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            UserDto userDto = convertToDto(user.get()); // Вспомогательный метод для преобразования User в UserDto
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        return dto;
    }
}