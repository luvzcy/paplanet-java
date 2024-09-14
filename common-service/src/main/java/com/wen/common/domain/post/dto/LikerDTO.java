package com.wen.common.domain.post.dto;

import lombok.Data;

/**
 * 点赞人信息
 */
@Data
public class LikerDTO {
    private Long userId;
    private String nickname;
    private String avatar;
}
