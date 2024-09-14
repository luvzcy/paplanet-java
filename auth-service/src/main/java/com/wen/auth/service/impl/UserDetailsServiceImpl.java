package com.wen.auth.service.impl;

import com.wen.auth.client.UserClient;
import com.wen.auth.mapper.PermissionMapper;
import com.wen.auth.mapper.RoleMapper;
import com.wen.common.domain.auth.Permission;
import com.wen.common.domain.auth.Role;
import com.wen.common.domain.auth.dto.LoginUser;
import com.wen.common.domain.user.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserClient userinfoMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Userinfo sysUser = userClient.getUserByUsername(username);

        //校验用户名密码是否正确
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        List<Role> roles = roleMapper.selectRolesByUserId(sysUser.getId());
        List<Permission> permissions = permissionMapper.selectPermissionByUserId(sysUser.getId());

        //构建用户的权限列表
        List<String> authorities = new ArrayList<>();
        for(Role role : roles){
            authorities.add("ROLE_" + role.getRoleName());
        }

        for(Permission permission : permissions){
            authorities.add(permission.getPermit());
        }

        return new LoginUser(sysUser,authorities);
    }



}
