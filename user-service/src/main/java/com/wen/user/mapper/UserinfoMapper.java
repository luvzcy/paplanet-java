package com.wen.user.mapper;


import com.wen.common.domain.user.Userinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserinfoMapper {

    //根据用户名查询用户
    @Select("SELECT * FROM userinfo WHERE username = #{username}")
    Userinfo selectUserByUsername(String username);

    //根据用户名id查询用户
    @Select("SELECT * FROM userinfo WHERE id = #{id}")
    Userinfo selectUserById(Long id);

}
