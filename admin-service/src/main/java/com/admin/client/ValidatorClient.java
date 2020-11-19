package com.admin.client;

import com.admin.entity.User;
import com.admin.entity.ValidatedUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "validator-service", url = "${validator.service.url}")
@RequestMapping("/api")
public interface ValidatorClient {

    @PatchMapping("/validate/user/{id}")
    User validateUserById(@PathVariable("id") Long id, @RequestBody ValidatedUser validated);
}