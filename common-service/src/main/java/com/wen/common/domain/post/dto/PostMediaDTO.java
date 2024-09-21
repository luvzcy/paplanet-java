package com.wen.common.domain.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostMediaDTO {
    private String type;
    private List<String> urls;
    private String videoId;
}
