package com.wen.common.domain.notification;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrivateMessage {
    private Long messageId;       // 消息ID，主键
    private Long senderId;        // 发送者ID
    private Long receiverId;      // 接收者ID
    private String messageType;   // 消息类型，如 "text", "image", "emoji"
    private String messageContent; // 消息内容，根据类型存储文本或文件URL
    private Integer isRead;       // 是否已读
    private Integer isDeleted;        // 是否已删除
    private LocalDateTime deletedAt;  // 消息删除时间
    private Integer isRecalled;       // 是否已撤回
    private LocalDateTime recalledAt; // 消息撤回时间
    private LocalDateTime createdAt;  // 创建时间
    private LocalDateTime updatedAt;  // 更新时间
}
