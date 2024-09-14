package com.wen.common.domain.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Userinfo {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private int age;
    private String gender;
    private String mobile;
    private String email;
    private String avatar;//头像

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastLoginTime;//上一次登录时间

    private int enabled;//账号是否可用。默认为1（可用）
    private int notExpired;//是否过期。默认为1（没有过期）
    private int accountNotLocked;//账号是否锁定。默认为1（没有锁定）
    private int credentialsNotExpired;//证书（密码）是否过期。默认为1（没有过期）

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;//创建时间

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;//修改时间

    private Long updateUser;//修改人
}
