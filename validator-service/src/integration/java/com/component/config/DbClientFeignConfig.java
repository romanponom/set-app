package com.component.config;

import com.validator.client.DBClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients(clients = DBClient.class)
@RestController
@Configuration
@EnableAutoConfiguration
@RibbonClient(name = "db-service", configuration = RibbonConfig.class)
public class DbClientFeignConfig {

}
