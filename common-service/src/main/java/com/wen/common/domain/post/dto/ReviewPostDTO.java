package com.wen.common.domain.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewPostDTO {
    private Long postId;        // 帖子ID
    private Long reviewerId;    // 审核员ID
    private Long categoryId;    // 帖子类型ID
    private LocalDateTime reviewTime; // 审核时间
    private String status;      // 审核状态：1表示通过，2表示不通过
    private String comments;    // 审核意见
}
