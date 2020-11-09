package com.validator;

import feign.okhttp.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableWebMvc
public class ValidatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidatorApplication.class, args);
    }

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

}
