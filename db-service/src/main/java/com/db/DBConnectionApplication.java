package com.db;

import com.db.entity.User;
import com.db.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.db")
@EnableJpaRepositories(basePackages = "com.db.repository")
@EntityScan(basePackages = "com.db.entity")
public class DBConnectionApplication {
    public static void main(String[] args) {
        SpringApplication.run(DBConnectionApplication.class, args);
    }

    @Bean
    CommandLineRunner initDataBase(UserRepository userRepository) {
        return args -> userRepository.save(new User(0, "admin", "admin@aa.ww", true));
    }

    //@Bean
    //public DataSource datasource() {
    //    return DataSourceBuilder.create()
    //            .driverClassName("com.mysql.cj.jdbc.Driver")
    //            .url("jdbc:mysql://localhost:3306/user?allowPublicKeyRetrieval=true&useSSL=false")
    //            .username("set-app")
    //            .password("")
    //            .build();
    //}

}
