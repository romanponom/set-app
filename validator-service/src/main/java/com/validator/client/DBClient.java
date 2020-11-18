package com.validator.client;

import com.validator.entity.User;
import com.validator.entity.ValidatedUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "db-service", url = "${db.service.url}")
@RequestMapping("/api")
public interface DBClient {

    @PatchMapping("/validate-user/{id}")
    User validateUser(@PathVariable("id") Long id, @RequestBody ValidatedUser validated);
}
