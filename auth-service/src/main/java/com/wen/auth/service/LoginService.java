package com.wen.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wen.common.domain.user.Userinfo;
import com.wen.common.lang.ResponseVO;

public interface LoginService {

    ResponseVO<?> loginByUsername(Userinfo userinfo) throws JsonProcessingException;

    ResponseVO<?> logout();

}
