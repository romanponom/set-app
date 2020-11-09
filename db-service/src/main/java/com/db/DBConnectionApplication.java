package com.db;

import com.db.entity.User;
import com.db.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.db")
public class DBConnectionApplication {
    public static void main(String[] args) {
        SpringApplication.run(DBConnectionApplication.class, args);
    }

    @Bean
    CommandLineRunner initDataBase(UserRepository userRepository) {
        return args -> userRepository.save(new User(0, "admin", "admin@aa.ww", true));
    }

}
