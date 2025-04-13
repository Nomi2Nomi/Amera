package com.example.Amera.service;

import org.springframework.stereotype.Service;

import com.example.Amera.dto.UserDto;
import com.example.Amera.entity.User;
import com.example.Amera.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(UserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Пользователь уже существует");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // лучше зашифровать!
        userRepository.save(user);
    }
}
