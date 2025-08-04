package com.eventify.notification_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {
    private Long userId;
    private String userEmail;
    private String subject;
    private String message;
    private NotificationType type;
}
