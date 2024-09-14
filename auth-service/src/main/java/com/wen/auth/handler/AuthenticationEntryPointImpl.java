package com.wen.auth.handler;

import com.alibaba.fastjson.JSON;
import com.wen.common.lang.ResponseStatusEnum;
import com.wen.common.lang.ResponseVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ResponseVO<String> responseVO = new ResponseVO<>(ResponseStatusEnum.UNAUTHORIZED.getCode(), "用户认证失败请重新登录");
        String json = JSON.toJSONString(responseVO);

        //处理异常
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().println(json);

    }
}
