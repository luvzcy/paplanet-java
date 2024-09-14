package com.wen.common.domain.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewPostLog {
    private Long id;
    private Long postId;
    private Long reviewerId;
    private Long categoryId;
    private LocalDateTime reviewTime;
    private String status; // 审核状态：1通过，2不通过
    private String comments; //审核意见
}
