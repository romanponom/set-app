package com.admin.client;

import com.admin.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "db-service", url = "${db.service.url}")
@RequestMapping("/api")
public interface DBClient {

    @GetMapping("/all")
    List<User> getUsers();

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable("id") Long id);

    @PostMapping("/add-user")
    User addUser(@RequestBody User user);

    @PostMapping("/update-user/{id}")
    User updateUser(@RequestBody User user, @PathVariable("id") Long id);

    @DeleteMapping("/delete-user/{id}")
    User deleteUser(@PathVariable("id") Long id);

    @DeleteMapping("/delete-all-users")
    List<User> deleteAllUsers();


}
