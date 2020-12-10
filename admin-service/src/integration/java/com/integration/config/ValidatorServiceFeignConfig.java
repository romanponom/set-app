package com.integration.config;

import com.admin.client.ValidatorClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients(clients = ValidatorClient.class)
@RestController
@Configuration
@EnableAutoConfiguration
@RibbonClient(name = "validator-service", configuration = ValidatorServiceRibbonConfig.class)
public class ValidatorServiceFeignConfig {

}
