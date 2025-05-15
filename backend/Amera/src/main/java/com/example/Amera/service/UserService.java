package com.example.Amera.service;

import com.example.Amera.dto.UserDto;
import com.example.Amera.entity.User;
import com.example.Amera.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder; // Важно для работы с паролями

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Для хеширования паролей

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User updateUser(Long id, UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(userDto.getName());
            user.setSurname(userDto.getSurname());
            user.setPhone(userDto.getPhone());
            user.setEmail(userDto.getEmail());
            // Не обновляем пароль здесь, если это не требуется!
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}