package com.wen.notification.service.strategy;

import com.wen.common.domain.notification.UserNotification;

public interface NotificationStrategy {
    void handleNotification(UserNotification notification, String action);
}
