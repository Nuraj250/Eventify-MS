package com.eventify.notification_servic.service;

import com.eventify.notification_servic.dto.NotificationRequest;

public interface NotificationService {
    void sendNotification(NotificationRequest request);
}
