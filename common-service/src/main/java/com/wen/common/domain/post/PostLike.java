package com.wen.common.domain.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostLike {
    private Long id;
    private Long userId;
    private Long postId;
    private LocalDateTime createdAt;
}
