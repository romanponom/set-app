package com.component.config;

import com.validator.client.DBClient;
import com.validator.entity.User;
import com.validator.entity.ValidatedUser;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@EnableFeignClients(clients = DBClient.class)
@RestController
@Configuration
@EnableAutoConfiguration
@RibbonClient(name = "db-service", configuration = RibbonConfig.class)
public class DbClientFeignConfig {

    @PutMapping("/api/validate-user/{id}")
    public User validateUser(@PathVariable("id") Long id, @RequestBody ValidatedUser validated) {
        return new User(2L, "user", "user@ww.qq", true);
    }
}
