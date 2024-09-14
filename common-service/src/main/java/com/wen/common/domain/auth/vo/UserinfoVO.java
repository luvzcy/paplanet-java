package com.wen.common.domain.auth.vo;

import lombok.Data;

@Data
public class UserinfoVO {
    private Long id;
    private String username;
    private String nickname;
    private int age;
    private String gender;
    private String mobile;
    private String email;
    private String avatar;//头像
}
