package com.wen.common.domain.auth;

import lombok.Data;

@Data
public class UserRole {
    private int id;
    private Long userId;
    private int roleId;
}
