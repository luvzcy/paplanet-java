package com.wen.common.domain.auth.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;


@Data
public class UserVO {
    private Long id;
    private String username;
    private String nickname;
    private int age;
    private String gender;
    private String mobile;
    private String email;
    private String avatar;//头像
    private List<GrantedAuthority> authorityList;
    private Map<String,String> jwtMap;
}
