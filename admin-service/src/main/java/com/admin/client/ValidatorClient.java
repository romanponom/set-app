package com.admin.client;

import com.admin.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "validator-service", url = "${validator.service.url}")
@RequestMapping("/api")
public interface ValidatorClient {

    @PatchMapping("/validate/user")
    User validateUserById(@RequestBody User user);
}
