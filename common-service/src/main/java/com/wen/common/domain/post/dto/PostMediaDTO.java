package com.wen.common.domain.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostMediaDTO {
    private String mediaType;
    private List<String> mediaUrls;
}
