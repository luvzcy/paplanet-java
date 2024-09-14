package com.wen.common.domain.post.vo;

import lombok.Data;

@Data
public class NotificationMessage {
    private Long postId;
    private Long targetUserId;
    private String type; // "like" or "cancel_like"
    private String content; // JSON 格式的通知内容

    public NotificationMessage(Long postId, Long targetUserId, String type, String jsonContent) {
        this.postId = postId;
        this.targetUserId = targetUserId;
        this.type = type;
        this.content = jsonContent;
    }
}
