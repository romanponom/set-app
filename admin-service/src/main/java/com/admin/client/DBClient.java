package com.admin.client;

import com.admin.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "db-service")
public interface DBClient {

    @GetMapping("/api/all")
    List<User> getUsers();

    @GetMapping("/api/user/{id}")
    User getUserById(@PathVariable("id") Long id);

    @PostMapping("/api/add-user")
    User addUser(@RequestBody User user);

    @PostMapping("/api/update-user/{id}")
    User updateUser(@RequestBody User user, @PathVariable("id") Long id);

    @DeleteMapping("/api/delete-user/{id}")
    User deleteUser(@PathVariable("id") Long id);

    @DeleteMapping("/api/delete-all-users")
    List<User> deleteAllUsers();


}
