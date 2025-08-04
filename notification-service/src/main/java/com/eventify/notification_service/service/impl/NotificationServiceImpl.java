package com.eventify.notification_servic.service.impl;

import com.eventify.notification_servic.dto.NotificationRequest;
import com.eventify.notification_servic.model.NotificationType;
import com.eventify.notification_servic.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(NotificationRequest request) {
        // Simulate sending email or SMS
        log.info("🔔 Sending {} to user {} <{}>",
                request.getType(), request.getUserId(), request.getUserEmail());

        log.info("📨 Subject: {}", request.getSubject());
        log.info("📝 Message: {}", request.getMessage());

        // Future: Add actual integration with email API
        if (request.getType() == NotificationType.ADMIN_ALERT) {
            log.info("⚠️ Alert sent to admin for user ID {}", request.getUserId());
        }
    }
}
