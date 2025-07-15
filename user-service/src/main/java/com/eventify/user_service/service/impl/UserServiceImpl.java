package com.eventify.user_service.service.impl;

import com.eventify.user.dto.AuthResponse;
import com.eventify.user.dto.LoginRequest;
import com.eventify.user.dto.UserDTO;
import com.eventify.user.model.User;
import com.eventify.user.repository.UserRepository;
import com.eventify.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse registerUser(UserDTO dto) {
        Optional<User> existing = userRepository.findByEmail(dto.getEmail());
        if (existing.isPresent()) {
            return new AuthResponse("Email already exists", null);
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);
        return new AuthResponse("User registered successfully", null);
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return new AuthResponse("Login successful", null); // Later: include JWT token
            }
        }
        return new AuthResponse("Invalid credentials", null);
    }
}
