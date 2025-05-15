package com.example.Amera.controller;

import com.example.Amera.dto.LoginRequest;
import com.example.Amera.dto.LoginResponse;
import com.example.Amera.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login attempt with email: " + loginRequest.getEmail()); // Логируем входные данные

        // Аутентификация и получение токена
        String token = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        if (token == null) {
            System.out.println("Token creation failed.");
            LoginResponse response = new LoginResponse();
            response.setErrorMessage("Invalid credentials");
            return ResponseEntity.badRequest().body(response);
        }

        // Извлечение userId из токена
        Long userId = authenticationService.extractUserIdFromToken(token);

        return ResponseEntity.ok(new LoginResponse(token, userId));
    }

}

