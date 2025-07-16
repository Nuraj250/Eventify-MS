package com.eventify.user_service.service;

import com.eventify.user.dto.AuthResponse;
import com.eventify.user.dto.LoginRequest;
import com.eventify.user.dto.UserDTO;

public interface UserService {
    AuthResponse registerUser(UserDTO userDTO);

    AuthResponse loginUser(LoginRequest loginRequest);
}
