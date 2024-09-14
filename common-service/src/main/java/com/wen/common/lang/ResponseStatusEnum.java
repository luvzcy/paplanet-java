package com.wen.common.lang;

import cn.hutool.http.HttpStatus;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum ResponseStatusEnum {
    FORBIDDEN(HttpStatus.HTTP_FORBIDDEN, "Forbidden"),        // 403 禁止访问
    UNAUTHORIZED(HttpStatus.HTTP_UNAUTHORIZED, "Unauthorized"), // 401 未经授权
    SUCCESS(HttpStatus.HTTP_OK, "OK"),                         // 200 请求成功
    BAD_REQUEST(HttpStatus.HTTP_BAD_REQUEST, "Bad Request"),    // 400 请求错误
    SYSTEM_ERROR(HttpStatus.HTTP_INTERNAL_ERROR, "系统异常错误"), // 500 服务器内部错误
    NOT_EXIST(HttpStatus.HTTP_NOT_FOUND, "请求路径不存在");      // 404 请求路径不存在


    /**
     * 返回的HTTP状态码,  符合http请求
     */
    private HttpStatus httpStatus;
    /**
     * 业务异常码
     */
    private final Integer code;
    /**
     * 业务异常信息描述
     */
    private final String msg;

    ResponseStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
