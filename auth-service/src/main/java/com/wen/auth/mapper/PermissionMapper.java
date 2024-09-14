package com.wen.auth.mapper;

import com.wen.common.domain.auth.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper {

    //通过userId查询员工的权限
    @Select("SELECT p.* FROM permission p, role_permission rpr, user_role srr " +
            "WHERE p.id = rpr.permission_id AND rpr.role_id = srr.role_id AND srr.user_id = #{userId}")
    List<Permission> selectPermissionByUserId(Long userId);

}
