package com.wen.common.domain.notification;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserUnreadCount {
    private Long id;                   // 自增主键
    private Long userId;               // 用户ID，主键
    private String messageType;        // 类型，如：message、friend
    private Integer unreadCount;       // 各类型未读消息数，使用Map存储，键为类型，值为数量
    private LocalDateTime updatedAt;  // 更新时间
}
