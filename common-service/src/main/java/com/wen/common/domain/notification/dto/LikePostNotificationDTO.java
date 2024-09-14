package com.wen.common.domain.notification.dto;

import com.wen.common.domain.notification.UserNotification;
import lombok.Data;

@Data
public class LikePostNotificationDTO {
    private String action; // like or cancel_like
    private UserNotification notification; // UserNotification class
}
