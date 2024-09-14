package com.wen.user.controller;

import com.wen.common.domain.user.Userinfo;
import com.wen.common.domain.auth.vo.UserinfoVO;
import com.wen.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 用于 openfeign 通信，获取用户信息通过 ID
    @GetMapping("/byId/{id}")
    public UserinfoVO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // 用于 openfeign 通信，获取用户信息通过用户名
    @GetMapping("/byUsername/{username}")
    public Userinfo getUserByUsername(@PathVariable String username) {
        System.out.println("username: " + username);
        return userService.getUserByUsername(username);
    }
}

