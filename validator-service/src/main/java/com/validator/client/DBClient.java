package com.validator.client;

import com.validator.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "db-service", url = "${db.service.url}")
@RequestMapping("/api")
public interface DBClient {

    @PatchMapping("/validate-user")
    User validateUser(@RequestBody User user);
}
