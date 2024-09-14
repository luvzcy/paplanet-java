package com.wen.common.domain.auth;

import lombok.Data;

@Data
public class RolePermission {
    private int id;
    private int roleId;
    private int perId;//权限编号
}
