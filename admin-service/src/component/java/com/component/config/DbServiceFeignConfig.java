package com.component.config;

import com.admin.client.DBClient;
import com.admin.entity.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@EnableFeignClients(clients = DBClient.class)
@RestController
@Configuration
@EnableAutoConfiguration
@RibbonClient(name = "db-service", configuration = RibbonConfig.class)
public class DbServiceFeignConfig {

    @GetMapping("/api/all")
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "admin", "admin@ww.qq", true));
        users.add(new User(2L, "user", "user@ww.qq", false));
        return users;
    }

    @GetMapping("/api/user/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return new User(id, "admin", "admin@ww.qq", true);
    }

    @PostMapping("/api/add-user")
    public User addUser(@RequestBody User user) {
        return new User(1L, "admin", "admin@ww.qq", true);
    }

    @PostMapping("/api/update-user/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        return new User(id, user.getName(), user.getEmail(), false);
    }

    @DeleteMapping("/api/delete-user/{id}")
    public User deleteUser(@PathVariable("id") Long id) {
        return new User(id, "admin", "admin@ww.qq", true);
    }

    @DeleteMapping("/api/delete-all-users")
    public List<User> deleteAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "admin", "admin@ww.qq", true));
        users.add(new User(2L, "user", "user@ww.qq", false));
        return users;
    }
}
