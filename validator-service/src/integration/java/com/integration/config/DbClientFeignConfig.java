package com.integration.config;

import com.validator.client.DBClient;
import com.validator.entity.User;
import com.validator.entity.ValidatedUser;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@EnableFeignClients(clients = DBClient.class)
@RestController
@Configuration
@EnableAutoConfiguration
@RibbonClient(name = "db-service", configuration = RibbonConfig.class)
public class DbClientFeignConfig {

    @PatchMapping("/api/validate-user/{id}")
    public User validateUser(@PathVariable("id") Long id, @RequestBody ValidatedUser validated) {
        return new User(1, "aaa", "aaa@aa.qq", true);
    }
}
