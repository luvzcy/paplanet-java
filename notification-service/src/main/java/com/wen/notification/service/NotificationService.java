package com.wen.notification.service;

import com.wen.common.domain.notification.UserNotification;

public interface NotificationService {
    void handleNotification(UserNotification notification, String action);
}
