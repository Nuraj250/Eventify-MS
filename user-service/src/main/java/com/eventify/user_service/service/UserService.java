package com.eventify.user_service.service;

import com.eventify.user_service.dto.AuthResponse;
import com.eventify.user_service.dto.LoginRequest;
import com.eventify.user_service.dto.UserDTO;

public interface UserService {
    AuthResponse registerUser(UserDTO userDTO);

    AuthResponse loginUser(LoginRequest loginRequest);

    void updateUserRole(Long userId, String newRole);
}
