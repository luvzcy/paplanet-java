package com.wen.user.service;

import com.wen.common.domain.auth.vo.UserinfoVO;
import com.wen.common.domain.user.Userinfo;

public interface UserService {
    // 根据用户id获取用户基本信息
    UserinfoVO getUserById(Long id);

    // 根据用户名获取用户详细信息
    Userinfo getUserByUsername(String username);
}
