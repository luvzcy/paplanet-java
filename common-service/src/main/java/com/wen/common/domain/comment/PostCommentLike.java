package com.wen.common.domain.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostCommentLike {
    private Long id;
    private Long userId;
    private Long commentId;
    private LocalDateTime createAt;
}
