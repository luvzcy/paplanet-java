package com.wen.common.domain.auth;

import lombok.Data;

@Data
public class Permission {
    private int id;
    private String perName;
    private int parentId;
    private String url;
    private String type;
    private String permit;
    private String description;//权限描述
}
