package com.wen.auth.client;

import com.wen.common.domain.user.Userinfo;
import com.wen.common.domain.auth.vo.UserinfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/user/byId/{id}")
    UserinfoVO getUserById(@PathVariable("id") Long id);

    @GetMapping("/user/byUsername/{username}")
    Userinfo getUserByUsername(@PathVariable("username") String username);
}
