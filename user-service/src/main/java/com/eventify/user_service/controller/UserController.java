package com.eventify.user_service.controller;

import com.eventify.user_service.dto.AuthResponse;
import com.eventify.user_service.dto.LoginRequest;
import com.eventify.user_service.dto.UserDTO;
import com.eventify.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        UserDTO userDTO = UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Welcome Admin! This is your dashboard.");
    }

}
