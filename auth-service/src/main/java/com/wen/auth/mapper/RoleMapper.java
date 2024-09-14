package com.wen.auth.mapper;



import com.wen.common.domain.auth.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {

    //根据用户id查询用户的角色
    @Select("SELECT r.* FROM role r, user_role srr WHERE r.id = srr.role_id AND srr.user_id = #{userId}")
    List<Role> selectRolesByUserId(Long userId);

}
