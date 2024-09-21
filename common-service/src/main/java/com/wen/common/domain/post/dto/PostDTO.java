package com.wen.common.domain.post.dto;

import lombok.Data;

import java.util.List;

/**
 * 发布帖子DTO
 */

@Data
public class PostDTO {
    private Long userId;
    private String content;
    private List<String> topics;
    private Double location;
    private PostMediaDTO medias;
}
