package com.validator.client;

import com.validator.entity.User;
import com.validator.entity.ValidatedUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("db-service")
public interface DBClient {

    @PutMapping("/api/validate-user/{id}")
    User validateUser(@PathVariable("id") Long id, @RequestBody ValidatedUser validated);
}
