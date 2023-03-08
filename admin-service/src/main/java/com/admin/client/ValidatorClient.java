package com.admin.client;

import com.admin.entity.User;
import com.admin.entity.ValidatedUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("validator-service")
public interface ValidatorClient {

    @PutMapping("/api/validate/user/{id}")
    User validateUserById(@PathVariable("id") Long id, @RequestBody ValidatedUser validated);
}
