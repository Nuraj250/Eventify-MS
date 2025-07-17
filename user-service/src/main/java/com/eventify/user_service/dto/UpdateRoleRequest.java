package com.eventify.user_service.dto;

import lombok.*;

@Getter
@Setter
public class UpdateRoleRequest {
    private String newRole; // "USER" or "ADMIN"
}
