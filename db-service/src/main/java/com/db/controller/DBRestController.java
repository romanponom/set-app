package com.db.controller;

import com.db.entity.User;
import com.db.entity.ValidatedUser;
import com.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor
public class DBRestController {

    private final UserService userService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/add-user")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return userService.addOrUpdateUser(user);
    }

    @PostMapping("/update-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        userService.getUser(id);
        user.setId(id);
        user.setValidated(false);
        return userService.addOrUpdateUser(user);
    }

    @DeleteMapping("/delete-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User deleteUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        userService.deleteUser(user);
        return user;
    }

    @DeleteMapping("/delete-all-users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> deleteAllUsers() {
        return userService.deleteAllUsers();
    }

    @PatchMapping(value = "/validate-user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User validateUser(@PathVariable long id, @RequestBody ValidatedUser validated) {
        User user = userService.getUser(id);
        user.setValidated(validated.isValidated());
        userService.addOrUpdateUser(user);
        return user;
    }

}
