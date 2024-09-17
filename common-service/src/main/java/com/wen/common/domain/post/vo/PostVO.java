package com.wen.common.domain.post.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostVO {
    private Long userId;
    private String content;
    private String category;
    private List<String> topics;
    private String type;
    private MediaVO media;
    private AuthorVO author;
    private Double location;
    private LocalDateTime createdAt;
    private Integer likeFlag; // 0: 未点赞，1：已点赞
    private Integer saveFlag; // 0: 未收藏，1：已收藏
}
