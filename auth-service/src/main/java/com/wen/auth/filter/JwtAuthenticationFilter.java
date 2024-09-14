package com.wen.auth.filter;

import com.wen.common.domain.auth.dto.LoginUser;
import com.wen.common.utils.JwtUtils;
import com.wen.common.utils.KryoUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 获取token
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token
        Claims claimByToken = jwtUtils.getClaimByToken(token);
        String userId = claimByToken.getSubject();

        // 从redis中获取用户信息
        String serializedLoginUserFromRedis = stringRedisTemplate.opsForValue().get("login:" + userId);


        if (serializedLoginUserFromRedis == null) {
            throw new RuntimeException("用户未登录");
        }

        // 反序列化 LoginUser 对象
        LoginUser loginUser = KryoUtil.deserializeFromString(serializedLoginUserFromRedis, LoginUser.class);

        // 获取权限信息封装到Authentication中
        Collection<? extends GrantedAuthority> authorities = loginUser.getAuthorities();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, authorities);

        // 存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}

