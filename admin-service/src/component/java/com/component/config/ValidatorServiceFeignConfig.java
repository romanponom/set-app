package com.component.config;

import com.admin.client.ValidatorClient;
import com.admin.entity.User;
import com.admin.entity.ValidatedUser;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients(clients = ValidatorClient.class)
@RestController
@Configuration
@EnableAutoConfiguration
@RibbonClient(name = "validator-service", configuration = RibbonConfig.class)
public class ValidatorServiceFeignConfig {

    @PatchMapping("/api/validate/user/{id}")
    public User validateUserById(@PathVariable("id") Long id, @RequestBody ValidatedUser validated) {
        return new User(id, "user", "user@ww.qq", validated.isValidated());
    }
}
