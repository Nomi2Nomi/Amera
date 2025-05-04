package com.example.Amera.service;

import com.example.Amera.entity.User;
import com.example.Amera.repository.UserRepository;
import com.example.Amera.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public String authenticate(String email, String password) {
        // Найти пользователя по email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            System.out.println("User not found for email: " + email);
            return null;
        }

        User user = userOptional.get();
        
        // Проверка пароля
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Invalid password for user: " + email);
            return null;
        }

        // Генерация токена
        String token = jwtTokenProvider.createToken(user.getEmail());
        if (token == null) {
            System.out.println("Token creation failed.");
            return null;
        }

        return token;
    }

}
