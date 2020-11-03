package com.controller;

import com.entity.User;
import com.exception.UserNotFoundException;
import com.repository.UserRepository;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class RestAppController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return user;
    }

    @PostMapping("add-user")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return userService.addOrUpdateUser(user);
    }

    @PostMapping("update-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        userService.findById(id);
        user.setId(id);
        return userService.addOrUpdateUser(user);
    }
    @DeleteMapping("delete-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        userService.deleteUser(user);
        return user;
    }

}
