package com.wen.common.domain.comment.dto;

import lombok.Data;

/**
 * 评论人的信息
 */
@Data
public class CommentatorDTO {
    private Long userId;
    private String nickname;
    private String avatar;
}
