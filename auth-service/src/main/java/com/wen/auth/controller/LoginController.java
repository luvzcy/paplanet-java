package com.wen.auth.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wen.auth.service.LoginService;
import com.wen.common.domain.auth.dto.LoginDTO;
import com.wen.common.domain.user.Userinfo;
import com.wen.common.lang.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/username")
    public ResponseVO<?> loginByUsername(@RequestBody LoginDTO loginDTO) throws JsonProcessingException {
        System.out.println("loginDTO:" + loginDTO);
        Userinfo sysUser = new Userinfo();
        BeanUtil.copyProperties(loginDTO,sysUser);
        return loginService.loginByUsername(sysUser);
    }

    @GetMapping("/logout")
    public ResponseVO<?> logout() {
        return loginService.logout();
    }

}
