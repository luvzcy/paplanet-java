package com.wen.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.wen.common.domain.user.Userinfo;
import com.wen.common.domain.auth.vo.UserinfoVO;
import com.wen.user.mapper.UserinfoMapper;
import com.wen.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserinfoMapper userinfoMapper;

    @Override
    public UserinfoVO getUserById(Long id) {
        Userinfo userinfo = userinfoMapper.selectUserById(id);
        UserinfoVO userinfoVO = new UserinfoVO();
        BeanUtil.copyProperties(userinfo, userinfoVO);
        return userinfoVO;
    }

    @Override
    public Userinfo getUserByUsername(String username) {
        return userinfoMapper.selectUserByUsername(username);
    }


}
