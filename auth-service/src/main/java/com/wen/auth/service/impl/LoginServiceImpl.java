package com.wen.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wen.auth.service.LoginService;
import com.wen.common.domain.auth.dto.LoginUser;
import com.wen.common.domain.auth.vo.UserVO;
import com.wen.common.domain.user.Userinfo;
import com.wen.common.lang.ResponseStatusEnum;
import com.wen.common.lang.ResponseVO;
import com.wen.common.utils.JwtUtils;
import com.wen.common.utils.KryoUtil;
import com.wen.common.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    JwtUtils jwtUtils;

    @Override
    public ResponseVO<?> loginByUsername(Userinfo userinfo) throws JsonProcessingException {

        // 获取AuthenticationManager的authenticate()进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userinfo.getUsername(), userinfo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 如果认证没通过，给出提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        // 如果认证通过，使用userId生成一个jwt，jwt存入ResponseEntity进行返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = String.valueOf(loginUser.getUserinfo().getId());
        String jwt = jwtUtils.generateToken(userId);

        // 生成jwtMap用于返回前端
        Map<String, String> jwtMap = new HashMap<>();
        jwtMap.put("jwt", jwt);

        // 序列化 LoginUser 对象并存入 Redis
        String serializedLoginUser = KryoUtil.serializeToString(loginUser);
        stringRedisTemplate.opsForValue().set("login:" + userId, serializedLoginUser);


        String serializedLoginUserFromRedis = stringRedisTemplate.opsForValue().get("login:" + userId);
        LoginUser loginUserFromRedis = KryoUtil.deserializeFromString(serializedLoginUserFromRedis, LoginUser.class);

        UserVO userInfoVo = new UserVO();
        userInfoVo.setJwtMap(jwtMap);
        userInfoVo.setAuthorityList(loginUserFromRedis.getAuthorityList());
        BeanUtil.copyProperties(loginUserFromRedis.getUserinfo(), userInfoVo);


        return ResponseVO.success(ResponseStatusEnum.SUCCESS, userInfoVo);
    }

    @Override
    public ResponseVO<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LoginUser loginUser) {
            Long userId = loginUser.getUserinfo().getId();
            // 删除redis中的值
            stringRedisTemplate.delete("login:" + userId);
            return ResponseVO.success(ResponseStatusEnum.SUCCESS, "注销成功");
        } else {
            return ResponseVO.failure(ResponseStatusEnum.BAD_REQUEST, "用户未登录");
        }
    }
}
