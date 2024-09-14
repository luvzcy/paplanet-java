package com.wen.post.client;

import com.wen.common.domain.auth.vo.UserinfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/user/byId/{id}")
    UserinfoVO getUserById(@PathVariable("id") Long id);
}
