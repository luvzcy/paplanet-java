package com.wen.auth.handler;


import com.alibaba.fastjson2.JSON;
import com.wen.common.lang.ResponseStatusEnum;
import com.wen.common.lang.ResponseVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ResponseVO<String> responseVO = new ResponseVO<>(ResponseStatusEnum.UNAUTHORIZED.getCode(), "您的权限不足");
        String json = JSON.toJSONString(responseVO);

        //处理异常
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().println(json);

    }
}
