package com.wen.common.domain.notification;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserNotification {
    private Long id;                    // 通知ID，主键
    private Long targetUserId;          // 用户ID，接收通知的用户
    private Long targetId;              // 通知目标ID，如点赞的文章ID
    private String targetType;          // 通知目标类型，如 "article", "comment"
    private String type;                // 通知类型，如 "like", "comment", "mention", "system"
    private String content;             // 通知内容描述，通常是JSON格式的数据
    private Integer isRead;             // 是否已读，0表示未读，1表示已读
    private LocalDateTime createdAt;    // 创建时间
    private LocalDateTime updatedAt;    // 更新时间
    private Integer delFlag;            // 是否已删除，0表示未删除，1表示已删除
}
