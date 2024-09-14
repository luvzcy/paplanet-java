package com.wen.common.domain.notification;

import lombok.Data;

@Data
public class NotificationSettings {
    private Long userId;                  // 用户ID，主键
    private Integer likeNotificationsEnabled;   // 点赞通知是否开启
    private Integer commentNotificationsEnabled; // 评论通知是否开启
    private Integer mentionNotificationsEnabled; // @通知是否开启
    private Integer systemNotificationsEnabled;  // 系统通知是否开启
}
