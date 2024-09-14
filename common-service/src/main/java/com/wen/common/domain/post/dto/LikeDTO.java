package com.wen.common.domain.post.dto;

import lombok.Data;

/**
 * 点赞接收参数
 */
@Data
public class LikeDTO {
    private Long postId;
    private LikerDTO liker;
    private Long targetUserId;
}
