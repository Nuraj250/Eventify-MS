package com.eventify.user_service.service.impl;

import com.eventify.user_service.dto.AuthResponse;
import com.eventify.user_service.dto.LoginRequest;
import com.eventify.user_service.dto.UserDTO;
import com.eventify.user_service.model.User;
import com.eventify.user_service.repository.UserRepository;
import com.eventify.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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
                String token = jwtService.generateToken(user.getEmail(), user.getRole());
                return new AuthResponse("Login successful", token);
            }
        }
        return new AuthResponse("Invalid credentials", null);
    }

    @Override
    public void updateUserRole(Long userId, String newRole) {
        List<String> allowedRoles = List.of("USER", "ADMIN");

        if (!allowedRoles.contains(newRole.toUpperCase())) {
            throw new IllegalArgumentException("Invalid role: " + newRole);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole.toUpperCase());
        userRepository.save(user);
    }
}
